package com.sameer.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testSendMail() {
        redisTemplate.opsForValue().set("email", "xyz@gmail.com");
        Object email = redisTemplate.opsForValue().get("salary");
        System.out.println(email);
        int a = 1;

    }
}
