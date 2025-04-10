package com.sameer.journalApp.service;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepo repo;

    public User SaveEntry(User user) {
        return repo.save(user);
    }
    public List<User> getAll() {return repo.findAll();}

    public Optional<User> getEntryById(ObjectId id) {
        return repo.findById(id);
    }

    public void deleteById(ObjectId id) {
        repo.deleteById(id);
    }
    public Optional<User> findById(ObjectId id) {
        return repo.findById(id);
    }

    public Optional<User> findByuserName(String username) {
        return repo.findByUsername(username);
    }
}
