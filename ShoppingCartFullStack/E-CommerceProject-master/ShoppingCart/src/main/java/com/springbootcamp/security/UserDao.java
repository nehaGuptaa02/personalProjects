package com.springbootcamp.security;

import com.springbootcamp.models.User;
import com.springbootcamp.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDao {
    @Autowired
    private UserRepository userRepository;
    public User loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if (user != null) {
            return new User(user);
        } else {
            throw new UsernameNotFoundException("user  " + user.getEmail() + " was not found");
        }

    }

}

