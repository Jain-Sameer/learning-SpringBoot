package com.sameer.journalApp.repository;

import com.sameer.journalApp.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserRepoIMPLTests {

    @Autowired
    private UserRepoIMPL userRepoIMPL;

    @Test
    public void testSaveNewUser(){
        userRepoIMPL.getUserForSA();
    }
}

