package com.sameer.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

}


// REST -> Representational State Transfer
// API -> Application Programming Interface
// LogBack, Log4j2, JUL -> Logging frameworks
// Logging Levels -? Trace, Debug, Info, Earn, Error
// SLF4J -> simple logging fascade for java, a logging abstraction for java