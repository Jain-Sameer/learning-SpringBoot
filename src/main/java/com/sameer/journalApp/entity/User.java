package com.sameer.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull // part of lombok
    private String username;
    @NonNull
    private String password;
    @DBRef(lazy = false) // creates a links between User and the Journal Entries
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;
}
