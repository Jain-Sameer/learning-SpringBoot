package com.sameer.journalApp.service;

import com.sameer.journalApp.entity.JournalEntry;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class JournalEntryService {

    @Autowired
    private journalEntryRepository journalEntryRepo;
    @Autowired
    private UserService userService;


    @Transactional
    public ResponseEntity<?> SaveEntry(JournalEntry j_entry, String username) {
        User user = userService.findByuserName(username).orElse(null);
        if(user==null) return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        
        j_entry.setDate(LocalDate.now());
        JournalEntry journalEntry = journalEntryRepo.save(j_entry);
        user.getJournalEntries().add(journalEntry);

        // for some reason if our program stops in between, we need to make sure that the code which has been executed, reverts back.
        // @Transactional annotation helps us achieve that, we also need to configure in the Main Application @EnableTransactionalManagement
        // Now every method with @Transactional, will have its own transactional context corresponding to every @Transactional method
        // Transactional Context -> Container in which all the db operations will be treated as one, any one fails it rolls back.
        // This achieves atomicity and isolation, every request will have its own container
        // We have a PlatformTransactionalManager (interface), its implementation is MongoTransactionManager
        // We will create its bean
        // We can't test transactions on our local db, that is why either we create a replicaSet using rs.initiate() in mongosh or shift to an online hosted db, mongo atlas
        User user1 = userService.SaveEntry(user);

        if(user1 == null) return new ResponseEntity<>("User not updated with the Entry! User is null", HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>("Entry created!", HttpStatus.CREATED);
    }


    public ResponseEntity<?> SaveEntry(JournalEntry j_entry) {
        JournalEntry journalEntry = journalEntryRepo.save(j_entry);
        return new ResponseEntity<>("Entry created!" + journalEntry.toString(), HttpStatus.CREATED);
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
