package com.towfiq.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // In a real application, you'd load user details from a database
        // pass: test@1234
        return new User("test", "$2a$12$3Mx5XUvQYcQW26SPuOgXqu4mkvPVIBLIVBgSTHRr46UDCTpqhnSIK", new ArrayList<>());
    }
}
