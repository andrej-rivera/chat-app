package com.websocket.service;

import com.websocket.model.UserData;
import com.websocket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository userRepository, EmailService emailService) {
        this.repository = userRepository;
    }

    public List<UserData> allUsers() {
        List<UserData> users = new ArrayList<>();
        users.addAll(repository.findAll());
        return users;
    }
}
