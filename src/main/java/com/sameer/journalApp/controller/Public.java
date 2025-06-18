package com.sameer.journalApp.controller;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.service.UserDetailsServiceImpl;
import com.sameer.journalApp.service.UserService;
import com.sameer.journalApp.service.weatherService;
import com.sameer.journalApp.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;

@RestController
@RequestMapping("/public")
@Slf4j
public class Public {

    private static final Logger log = LoggerFactory.getLogger(Public.class);
    private final UserService userService;
    private final weatherService wService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtil jwtUtil;
    @Autowired
    public Public(UserService userService, weatherService wService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.wService = wService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/healthcheck")
    public String healthCheck() throws IOException {
        return "Okay!";
    }


    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        User status = userService.saveNewUser(user);
        if (status == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        return new ResponseEntity<>(status, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception occured while creating JWT ", e);
            return new ResponseEntity<>("Incorrect Username or Password", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/weather")
    public String weatherCheck(@RequestBody String city) {

        return "Hi! " +  wService.getWeather(city).getBody().main.temp;
    }
    @GetMapping("/test")
    public String testing() {
        String style = "color:red;";
        return "<h1 style=\"" +style+ "\">Sameer Jain</h1>";
    }
}
