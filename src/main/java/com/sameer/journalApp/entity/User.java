package com.sameer.journalApp.entity;

import com.mongodb.connection.ProxySettings;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull // part of lombok
    private String username;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef(lazy = false) // creates a links between User and the Journal Entries.
    private List<JournalEntry> journalEntries = new ArrayList<>();
    private List<String> roles;


    public void addRoles(String role) {
        roles.add(role);
    }
}
