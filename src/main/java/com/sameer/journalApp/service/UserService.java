package com.sameer.journalApp.service;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.repository.UserRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepo repo;


    private static final PasswordEncoder passwordEncoder=  new BCryptPasswordEncoder();

    public User saveUser(User user) {
        return repo.save(user);
    }

    public User saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
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
    public void deleteByusername(String username) {
        repo.deleteByUsername(username);
    }

    public User saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER", "ADMIN"));
        return repo.save(user);
    }
}
