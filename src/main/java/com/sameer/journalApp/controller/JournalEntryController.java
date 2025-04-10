package com.sameer.journalApp.controller;

import com.sameer.journalApp.entity.JournalEntry;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.JournalEntryService;
import com.sameer.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;
    @PostMapping("/create/{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry j_entry, @PathVariable String username) {
        try{
            return journalEntryService.SaveEntry(j_entry, username);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getEntriesByUser(@PathVariable String username) {
        User user = userService.findByuserName(username).orElse(null);
        if(user == null) return new ResponseEntity<>("UserNotFound",HttpStatus.NOT_FOUND);
        List<JournalEntry> entries = user.getJournalEntries();
        if(entries != null && !entries.isEmpty()) {
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Entries",HttpStatus.NOT_FOUND);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id) {
//        System.out.println("ObjectID : " + id);
        Optional<JournalEntry> entry = journalEntryService.findById(id);
        if(entry.isPresent()) {
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/delete/{username}/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id, @PathVariable String username) { // need to perform cascade delete
        return journalEntryService.deleteById(id, username);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/update/{username}/{id}")
    public ResponseEntity<?> updateByid(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry,@PathVariable String username) {
        User user = userService.findByuserName(username).orElse(null);
        if(user == null) {
            return new ResponseEntity<>("Can't update! User doesnt exist", HttpStatus.BAD_REQUEST);
        }
        JournalEntry oldentry = new JournalEntry();
        oldentry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).findFirst().orElse(null);
        if(oldentry == null) {
            return new ResponseEntity<>("Entry not found!", HttpStatus.NOT_FOUND);
        }

        oldentry.setContent(newEntry.getContent());
        oldentry.setTitle(newEntry.getTitle());
        journalEntryService.SaveEntry(oldentry);
        // created a new overloaded save entry service so that, we dont have two of same entries in the user db. cos It was saving it two times.
        // we don't need to do any changes in the user, only the entry. that is why.
        return new ResponseEntity<>("Everything is fine!", HttpStatus.OK);
    }
}

//ORM -> Object Relational Mapping
//JPA -> Java Persistent API -> A way to achieve the ORM functionality inside Java
// To  use JPA, we need a persistent provider i.e. specific implemenation of JPA specs.
// Spring DATA JPA built on top of JPA but not an implementaiton of JPA

//as MongoDB is a NoSQL db, ie, no schema, we cant use JPA with mongo

//controller ->> service -->> repository
//Lombok ->> Lombok is a popular java library, can be used in Spring Booot app.
// aims to reduce the boilerplaye code that we have to write, likes setters, getters, constructors, etc.