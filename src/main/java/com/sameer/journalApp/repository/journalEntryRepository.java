package com.sameer.journalApp.repository;

import com.sameer.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface journalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

}
