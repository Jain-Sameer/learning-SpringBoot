package com.sameer.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sameer.journalApp.apiresponse.weatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public <T> T get(String key, Class<T> clazz) {
        try {
            String json = redisTemplate.opsForValue().get(key);
            if (json == null) {
                return null;
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Error deserializing key {}: {}", key, e.getMessage());
            return null;
        }
    }

    public void set(String key, Object value, Long ttlSeconds) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonValue = mapper.writeValueAsString(value);
            redisTemplate.opsForValue().set(key, jsonValue, ttlSeconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Error serializing value for key {}: {}", key, e.getMessage());
        }
    }
}
