package com.sameer.journalApp.controller;

import com.sameer.journalApp.entity.JournalEntry;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.JournalEntryService;
import com.sameer.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal Entry API's")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;

    private final UserService userService;

    @Autowired
    JournalEntryController(JournalEntryService journalEntryService, UserService userService) {
        this.journalEntryService = journalEntryService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry j_entry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try{
            return journalEntryService.SaveEntry(j_entry, username);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Did it reach here ?",HttpStatus.BAD_REQUEST);
        }
    }
    // ResponseEntity<?> Wildcard Response Entity of Generic Type, we can send any type of information using this.

    @GetMapping("/get")
    public ResponseEntity<?> getEntriesByUser() {
        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        String username = user.getName();
        User userinDB = userService.findByuserName(username).orElse(null);
        if(userinDB == null) return new ResponseEntity<>("UserNotFound",HttpStatus.NOT_FOUND);
        List<JournalEntry> entries = userinDB.getJournalEntries();
        if(entries != null && !entries.isEmpty()) { // null and empty two different things, null -> entries array not found or doesnt exist, but empty means there is an entries array but it does not contain any entry in it.
            return new ResponseEntity<>(entries, HttpStatus.OK);
        }
        return new ResponseEntity<>("No Entries",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<String> getEntryById(@PathVariable ObjectId id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByuserName(username).orElse(null);

        if(user == null) return new ResponseEntity<>("User doesn't exist.", HttpStatus.BAD_REQUEST);


        List<JournalEntry> entries = user.getJournalEntries();
        for(JournalEntry entry : entries) {
            if(entry.getId().equals(id)) {
                return new ResponseEntity<>("Entry : "+ entry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("UnAuthorized or Doesn't exist",HttpStatus.NOT_FOUND);
    }

    @GetMapping("/entry")
    public ResponseEntity<String> getAllEntry() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByuserName(username).orElse(null);
        if(user == null) return new ResponseEntity<>("User doesn't exist.", HttpStatus.BAD_REQUEST);
        List<JournalEntry> entries = user.getJournalEntries();
        if(entries != null && !entries.isEmpty())
            return new ResponseEntity<>(entries.toString(),HttpStatus.OK);

        return new ResponseEntity<>("Not found!", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId id) { // need to perform cascade delete, such that reference and the actual are both deleted. ie. the entry is deleted from the Entries db and the User jentries list.
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return journalEntryService.deleteById(id, username);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateByid(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByuserName(username).orElse(null);
        if(user == null) {
            return new ResponseEntity<>("Can't update! User doesnt exist", HttpStatus.BAD_REQUEST);
        }
        JournalEntry oldentry = user.getJournalEntries().stream().filter(entry -> entry.getId().equals(id)).findFirst().orElse(null);
        if(oldentry == null) {
            return new ResponseEntity<>("Entry not found!", HttpStatus.NOT_FOUND);
        }

        oldentry.setContent(newEntry.getContent());
        oldentry.setTitle(newEntry.getTitle());
        journalEntryService.SaveEntry(oldentry);
        // created a new overloaded save entry service so that, we don't have two of same entries in the user db. cos It was saving it two times.
        // we don't need to do any changes in the user, only the entry. that is why.
        return new ResponseEntity<>("Everything is under control!", HttpStatus.OK);
    }
}

//ORM -> Object Relational Mapping
//JPA -> Java Persistent API -> A way to achieve the ORM functionality inside Java
// To  use JPA, we need a persistent provider i.e. specific implemenation of JPA specs.
// Spring DATA JPA built on top of JPA but not an implementaiton of JPA

//as MongoDB is a NoSQL db, ie, no schema, we cant use JPA with mongo, we cant also perform cascade delete in mongoDb, as it is a nosql db and we have to delete both the entries manually

//controller ->> service -->> repository
//Lombok ->> Lombok is a popular java library, can be used in Spring Booot app.
// aims to reduce the boilerplaye code that we have to write, likes setters, getters, constructors, etc.