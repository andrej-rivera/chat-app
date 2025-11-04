package com.websocket.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;

// Initialize annotation to generate boilerplate for User objects
@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    private String displayName; // display name for the user, any user can have the same display name
    private String username; // unique username for each user
    private String email; // unique email for each user (for signup)
    private String password; // hashed password
    private Status status;
    private HashSet<String> friends; // set of usernames representing friends
    private HashSet<String> receivingRequests; // usernames of users who sent friend requests
    private HashSet<String> sentRequests; // usernames of users to whom friend requests were sent

    // Token management
    private HashMap<String, Token> tokens;

    @AllArgsConstructor
    class Token {
        String tokenValue;
        Date expiryDate;
    }
}   
