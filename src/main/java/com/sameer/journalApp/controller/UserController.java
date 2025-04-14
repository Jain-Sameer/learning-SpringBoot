package com.sameer.journalApp.controller;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User newUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        User oldUser = userService.findByuserName(name).orElse(null);
        System.out.println(oldUser.getUsername());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setPassword(newUser.getPassword());
        User updatedUser = userService.saveNewUser(oldUser);
        if(updatedUser == null) return new ResponseEntity<>("null here ?",HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        userService.deleteByusername(name);
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