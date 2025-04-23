package com.sameer.journalApp.repository;

import com.sameer.journalApp.entity.Config;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepo extends MongoRepository<Config, ObjectId> {
}
