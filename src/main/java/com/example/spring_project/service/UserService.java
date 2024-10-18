package com.example.spring_project.service;

import org.springframework.stereotype.Service;

import com.example.spring_project.entity.User;
import com.example.spring_project.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository){ this.userRepository = userRepository; }
    public void addUser(User user){
        userRepository.saveAndFlush(user);
    }
    public User getUserByUsername(String username){ return userRepository.findByUsername(username); }

}
