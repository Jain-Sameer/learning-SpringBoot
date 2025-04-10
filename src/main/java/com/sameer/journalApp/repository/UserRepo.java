package com.sameer.journalApp.repository;

import com.sameer.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
}
