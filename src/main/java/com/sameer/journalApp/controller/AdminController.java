package com.sameer.journalApp.controller;

import com.sameer.journalApp.cache.AppCache;
import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.UserDetailsServiceImpl;
import com.sameer.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API's")
public class AdminController {

    @Autowired
    private AppCache appCache;

    private final UserService userService;
    AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.getAll();
        if(users != null && !users.isEmpty()) {
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createAdmin")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        User saveduser = userService.saveAdmin(user);
        if(saveduser == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(saveduser,HttpStatus.OK);
    }

    @GetMapping("/clear-app-cache")
    public ResponseEntity<HttpStatus> clearAppCache() {
        appCache.init();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
