package com.travel.demo.repository;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;
    
    public UserDetailsServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.travel.demo.model.User user = userRepo.findByEmail(username);
        System.out.println("User name "+username);
        if (user == null) {
        	System.out.println("User not found");
            throw new UsernameNotFoundException("User not found");
        }
        	

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}

