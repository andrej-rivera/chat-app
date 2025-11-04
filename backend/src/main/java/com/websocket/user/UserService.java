package com.websocket.user;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    // respository for all users
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    // Register a new user
    public User registerUser(User user) {
        if (repository.existsById(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (repository.existsById(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        // encode password & initialize other fields
        user.setPassword(encoder.encode(user.getPassword()));
        user.setStatus(Status.OFFLINE);
        user.setFriends(new HashSet<>());
        user.setReceivingRequests(new HashSet<>());
        user.setSentRequests(new HashSet<>());
        user.setTokens(new HashMap<>());
        return repository.save(user);
    }

    
    // Remove a user by username
    public void removeUser(String username) {
        repository.deleteById(username);
    }

    // Request a password reset
    public String requestPasswordReset(String email) {
        var user = repository.findById(email).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("No such account exists");
        }
        
        // Generate Token
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32]; // 32 bytes for a strong token
        secureRandom.nextBytes(tokenBytes);
        String secureToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

        // Set expiry date (e.g., 1 hour from now)
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        Date expiryDate = calendar.getTime();

        // Create and store token
        User.Token token = user.new Token(secureToken, expiryDate);
        user.getTokens().put("passwordToken", token);
        repository.save(user);

        return secureToken;

    }

    // Change a user's password
    public User changeUserPassword(String email, String newPassword, String token) {
        // obtain user
        var user = repository.findById(email).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("No such account exists");
        }

        // validate token
        var storedToken = user.getTokens().get("passwordToken");
        if (!token.equals(storedToken.tokenValue)) {
            throw new IllegalArgumentException("Invalid token");
        }
        if (storedToken.expiryDate.after(new Date())) {
            user.getTokens().remove("passwordToken"); // remove expired token
            throw new IllegalArgumentException("Token has expired");
        }

        // encode password
        user.setPassword(encoder.encode(newPassword));
        return repository.save(user);    
    }

    // Connect user to service (login)
    public void connectUser(User user) {
        // verify username and password exists before connecting
        var storedUser = repository.findById(user.getUsername()).orElse(null);
        if (storedUser == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!encoder.matches(user.getPassword(), storedUser.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        storedUser.setStatus(Status.ONLINE);
        repository.save(storedUser);
    }

    // Disconnect user from service
    public void disconnectUser(User user) {
        var storedUser = repository.findById(user.getUsername()).orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            repository.save(storedUser);
        }
    }

    // Return a list of all users online
    public User findByEmail(String email) {
        var storedUser = repository.findById(email).orElse(null);
        if (storedUser != null) {
            return storedUser;
        }
        return null;
    }

    // Return a list of all users online
    public List<User> findConnectedUsers() {
        return repository.findAllByStatus(Status.ONLINE);
    }

    // Return a list of all of the user's friends
    public List<User> findFriends(String username) {
        var user = repository.findById(username)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        // Fetch all friends and clear sensitive data
        return repository.findAllById(user.getFriends()).stream()
            .map(friend -> {
                friend.setPassword(null);  // Don't send passwords
                friend.setEmail(null);     // Protect private information
                return friend;
            })
            .toList();
    }


    // Send a friend request from one user to another
    @Transactional
    public void sendFriendRequest(User user, String friendUsername) {
        if (user.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }

        // search for user & friend in repository
        var storedUser = repository.findById(user.getUsername()).orElse(null);
        var storedFriend = repository.findById(friendUsername).orElse(null);

        // throw error if we don't find either user
        if (storedUser == null || storedFriend == null) {
            throw new IllegalArgumentException("Users not found");
        }

        // send friend request to friend
        storedFriend.getReceivingRequests().add(user.getUsername());
        storedUser.getSentRequests().add(friendUsername);
        repository.save(storedFriend);
        repository.save(storedUser);
    }

    // Accept a friend request from another user
    @Transactional
    public void acceptFriendRequest(User user, String friendUsername) {
        if (user.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }
        var storedUser = repository.findById(user.getUsername()).orElse(null);
        var storedFriend = repository.findById(friendUsername).orElse(null);

        if (storedUser == null || storedFriend == null) {
            throw new IllegalArgumentException("Users not found");
        }

        // add each other as friends
        storedUser.getFriends().add(friendUsername);
        storedFriend.getFriends().add(user.getUsername());

        // remove friend request
        storedUser.getReceivingRequests().remove(friendUsername);
        storedFriend.getSentRequests().remove(user.getUsername());

        repository.save(storedUser);
        repository.save(storedFriend);
    }

    // Decline a friend request from another user
    @Transactional
    public void declineFriendRequest(User user, String friendUsername) {
        if (user.getUsername().equals(friendUsername)) {
            throw new IllegalArgumentException("Cannot send friend request to yourself");
        }
        var storedUser = repository.findById(user.getUsername()).orElse(null);
        var storedFriend = repository.findById(friendUsername).orElse(null);

        if (storedUser == null || storedFriend == null) {
            throw new IllegalArgumentException("Users not found");
        }

        // remove friend request
        storedUser.getReceivingRequests().remove(friendUsername);
        storedFriend.getSentRequests().remove(user.getUsername());

        repository.save(storedUser);
        repository.save(storedFriend);
    }

}
