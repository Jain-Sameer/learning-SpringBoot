package com.sameer.journalApp.controller;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User status = userService.SaveEntry(user);
        if (status == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        if(users!=null) return new ResponseEntity<>(users, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/update/{u_name}")
    public ResponseEntity<?> updateUser(@RequestBody User newUser, @PathVariable String u_name) {
        User oldUser = userService.findByuserName(u_name).orElse(null);
        if(oldUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        User updatedUser = userService.SaveEntry(oldUser);
        if(updatedUser == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

//ORM -> Object Relational Mapping
//JPA -> Java Persistent API -> A way to achieve the ORM functionality inside Java
// To  use JPA, we need a persistent provider i.e. specific implemenation of JPA specs.
// Spring DATA JPA built on top of JPA but not an implementaiton of JPA

//as MongoDB is a NoSQL db, ie, no schema, we cant use JPA with mongo

//controller ->> service -->> repository
//Lombok ->> Lombok is a popular java library, can be used in Spring Booot app.
// aims to reduce the boilerplaye code that we have to write, likes setters, getters, constructors, etc.