package com.sameer.journalApp.service;

import com.sameer.journalApp.entity.User;
import com.sameer.journalApp.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import static org.springframework.security.core.userdetails.User.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {




    private final UserRepo repo;
    @Autowired
    public UserDetailsServiceImpl(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repo.findByUsername(username).orElse(null);

        if(user != null) {
            return builder().username(user.getUsername()).password(user.getPassword()).roles(user.getRoles().toArray(new String[0])).build();
        }
        throw new UsernameNotFoundException("user not found!");
    }
}
