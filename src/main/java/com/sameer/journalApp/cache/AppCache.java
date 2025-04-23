package com.sameer.journalApp.cache;

import com.sameer.journalApp.entity.Config;
import com.sameer.journalApp.repository.ConfigRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    ConfigRepo configRepo;

    public Map<String, String> cache;

    @PostConstruct
    public void init() {
        System.out.printf("Intiliased\n");
        cache = new HashMap<>();
        List<Config> all = configRepo.findAll();
        for(Config config : all) {
            cache.put(config.getKey(), config.getValue());
        }
    }
}
