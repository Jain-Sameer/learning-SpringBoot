package com.sameer.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
// using @Data requires us to set a NoArgsConstructor annotation cos it contains AllArgsConstructor annotation in itself
@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id // unique key in our document collection
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDate date;
}
