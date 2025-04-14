package com.sameer.journalApp.service;


import com.sameer.journalApp.entity.JournalEntry;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.repository.UserRepo;
import com.sameer.journalApp.repository.journalEntryRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTests {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private journalEntryRepository journalRepo;

    @Test
    public void testAdd() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testFindByUsername() {
        Optional<User> doesSameerexist = userRepo.findByUsername("sameer123123");
        System.out.println(doesSameerexist.get());
        assertNotNull(doesSameerexist.get());
    }

    @Test
    public void testUserCreated() {
        User user = new User();
        user.setUsername("sameer123123");
        user.setPassword("sameer");
        User save = userRepo.save(user);
        User byUsername = userRepo.findByUsername(save.getUsername()).orElse(null);

        assertEquals(byUsername.hashCode(), save.hashCode());
    }

    @Test
    public void testUserCanCreateEntry() {
        User sameer123123 = userRepo.findByUsername("sameer123123").orElse(null);
        assertNotNull(sameer123123, "User doesn;t exist");


        JournalEntry entry = new JournalEntry();
        entry.setTitle("JUnit Tests");
        entry.setContent("testing testing testing");
        journalRepo.save(entry);

        sameer123123.setJournalEntries(Arrays.asList(entry));
        userRepo.save(sameer123123);

        User updated = userRepo.findByUsername("sameer123123").orElse(null);
        assertNotNull(updated.getJournalEntries(), "No entries");

    }

    @Test public void entriesNotEmpty() {
        Optional<User> user = userRepo.findByUsername("sameer");
        assertFalse(user.get().getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    public void test(int a, int b, int expected) {
        assertEquals(expected, a+b);
    }

    
    
}
