package com.sameer.journalApp.controller;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.UserService;
import com.sameer.journalApp.service.weatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/public")
public class Public {

    private final UserService userService;
    private final weatherService wService;
    @Autowired
    public Public(UserService userService, weatherService wService) {
        this.userService = userService;
        this.wService = wService;
    }

    @GetMapping("/healthcheck")
    public String healthCheck() throws IOException {
        return "Okay!";
    }


    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User status = userService.saveNewUser(user);
        if (status == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @GetMapping("/weather")
    public String weatherCheck(@RequestBody String city) {
        System.out.println(city);
        return "Hi! " +  wService.getWeather(city).getBody().main.toString();
    }
    @GetMapping("/test")
    public String testing() {
        String style = "color:red;";
        return "<h1 style=\"" +style+ "\">Sameer Jain</h1>";
    }
}
