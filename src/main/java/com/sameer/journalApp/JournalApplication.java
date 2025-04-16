package com.sameer.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class JournalApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(JournalApplication.class, args);
		ConfigurableEnvironment environment = run.getEnvironment();
		System.out.println(environment.getActiveProfiles()[0]);
	}

}


// REST -> Representational State Transfer
// API -> Application Programming Interface
// LogBack, Log4j2, JUL -> Logging frameworks
// Logging Levels -? Trace, Debug, Info, Earn, Error
// slf4j -> simple logging fascade for java, a logging abstraction for java