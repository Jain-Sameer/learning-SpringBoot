package com.sameer.journalApp.service;

import com.sameer.journalApp.model.SentimentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SentimentConsumerService {
    @Autowired
    private EmailService emailService;

//    @KafkaListener(topics = "weekly-sentiments", groupId = "weekly-sentiment-group" )
//    public void consume(SentimentData sentimentData) {
//        emailService.sendEmail(sentimentData.getEmail(), "Sentiment for Previous Week ", sentimentData.getSentiment());
//    }
}
