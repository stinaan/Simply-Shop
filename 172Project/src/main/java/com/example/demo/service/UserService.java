package com.example.demo.service;
import java.util.Optional;

import com.example.demo.model.User;

public interface UserService {

    Optional<User> findByUsername(String userName);
    
    Optional<User> findByID(int userID);

    User saveUser(User user);
    
    void login(String username, String password);




}