package com.sameer.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class Config {
    @Bean
    public PlatformTransactionManager manager(MongoDatabaseFactory dbFactory) {
        // session -> transactional context
        return new MongoTransactionManager(dbFactory);
    }

}
