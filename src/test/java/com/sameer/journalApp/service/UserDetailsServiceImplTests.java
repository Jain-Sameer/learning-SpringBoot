package com.sameer.journalApp.service;

import com.sameer.journalApp.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.sameer.journalApp.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import static org.mockito.Mockito.*;


public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    UserRepo userRepo;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUsernameByUsernameTest() {
        when(userRepo.findByUsername(ArgumentMatchers.any()))
                .thenReturn(Optional.of(User.builder()
                        .username("sameer123")
                        .password("sameer123")
                        .roles(Arrays.asList("USER"))
                        .build()));
        UserDetails user = userDetailsService.loadUserByUsername("sameer123");
        assertNotNull(user);
    }
}
