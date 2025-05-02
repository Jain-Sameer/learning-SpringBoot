package com.sameer.journalApp.schedulers;

import com.sameer.journalApp.cache.AppCache;
import com.sameer.journalApp.entity.JournalEntry;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.enums.Sentiment;
import com.sameer.journalApp.repository.UserRepoIMPL;
import com.sameer.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserScheduler {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepoIMPL userRepoIMPL;

    @Autowired
    private AppCache appCache;

//    @Scheduled(cron = "0 25 20 * * Sat")
    @Scheduled(cron = "0 */5 * * * *")
    public void fetchUsersAndSendSAMail() {
        List<User> userForSA = userRepoIMPL.getUserForSA();
        for(User user : userForSA) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(ChronoLocalDate.from(LocalDateTime.now().minus(7, ChronoUnit.DAYS)))).map(x -> x.getSentiment()).toList();

            HashMap<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for(Sentiment sentiment : sentiments) {
                if (sentiment!=null) {
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }

            Sentiment mostFrequent = null;
            int maxCount = 0;
            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequent = entry.getKey();
                }
            }
            if(mostFrequent != null) {
                emailService.sendEmail("jainsameer2003@gmail.com", "Sentiment of Last 7 Days : ", mostFrequent.toString());
            }
        }
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void clearAppCache() {
        appCache.init();
    }
}
