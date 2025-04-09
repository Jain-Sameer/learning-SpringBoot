package com.sameer.journalApp.service;

import com.sameer.journalApp.entity.JournalEntry;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class JournalEntryService {

    @Autowired
    private journalEntryRepository journalEntryRepo;
    @Autowired
    private UserService userService;
    public ResponseEntity<?> SaveEntry(JournalEntry j_entry, String username) {
        User user = userService.findByuserName(username).orElse(null);
        if(user==null) return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        
        j_entry.setDate(LocalDate.now());
        JournalEntry journalEntry = journalEntryRepo.save(j_entry);
        user.getJournalEntries().add(journalEntry);
        User user1 = userService.SaveEntry(user);

        if(user1 == null) return new ResponseEntity<>("User not updated with the Entry!", HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>("Entry created!", HttpStatus.CREATED);
    }
    public ResponseEntity<?> SaveEntry(JournalEntry j_entry) {
        JournalEntry journalEntry = journalEntryRepo.save(j_entry);
        return new ResponseEntity<>("Entry created!", HttpStatus.CREATED);
    }
    public List<JournalEntry> getAll() {return journalEntryRepo.findAll();}

    public Optional<JournalEntry> getEntryById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    public ResponseEntity<?> deleteById(ObjectId id, String username) {

        User user = userService.findByuserName(username).orElse(null);
        if(user == null) return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
//        System.out.println("gagasgsdgsd " + user.getJournalEntries());
        user.getJournalEntries().removeIf(x -> id.equals(x.getId()));
        userService.SaveEntry(user);
        journalEntryRepo.deleteById(id);

        return new ResponseEntity<>("Entry deleted!", HttpStatus.OK);
    }
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }
}
