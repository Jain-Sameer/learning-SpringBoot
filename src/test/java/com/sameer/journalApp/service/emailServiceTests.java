package com.sameer.journalApp.service;

import com.sameer.journalApp.schedulers.UserScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class emailServiceTests {

    @Autowired EmailService emailService;
    @Autowired UserScheduler userScheduler;

    @Test
    public void testEmailSender() {
        String to = "jainsameer2003@gmail.com";
        String subject = "Testing SENDING EMAIL using Spring Boot";
        String text = "Hi there! It's me!";

        emailService.sendEmail(to, subject, text);
    }

    @Test public void testSA() {
        userScheduler.fetchUsersAndSendSAMail();
    }
}
