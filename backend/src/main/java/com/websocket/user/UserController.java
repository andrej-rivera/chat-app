package com.websocket.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    // Register a new user
    @PostMapping("/users/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User created = service.registerUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // Remove a user by username
    @PostMapping("/users/remove")
    public ResponseEntity<Void> removeUser(@RequestBody String username) {
        service.removeUser(username);
        return ResponseEntity.noContent().build();
    }

    // Request a password reset (generates a token for user)
    @PostMapping("/users/password/request")
    public ResponseEntity<Void> requestPasswordReset(@RequestBody String email) {
        service.requestPasswordReset(email);
        return ResponseEntity.ok().build();
    }

    // Remove a user by username
    @PostMapping("/users/password/change")
    public ResponseEntity<Void> changeUserPassword(@RequestBody String email, @RequestBody String newPassword, @RequestBody String token) {
        service.changeUserPassword(email, newPassword, token);
        return ResponseEntity.noContent().build();
    }

    // Login a user
    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User connectUser(@Payload User user) {
        service.connectUser(user);
        return user;
    }

    // Logout a user
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnectUser(@Payload User user) {
        service.disconnectUser(user);
        return user;
    }

    // Function to obtain all connectedUsers
    @GetMapping("/users/connectedUsers")
    public ResponseEntity<List<User>> findConnectedUsers() {
        var users = service.findConnectedUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{username}/friends")
    public ResponseEntity<List<User>> findFriends(@PathVariable String username) {
        var users = service.findFriends(username);
        return ResponseEntity.ok(users);

    }

    @PostMapping("/users/sendFriendRequest")
    public ResponseEntity<Void> sendFriendRequest(@RequestBody String senderUsername, @RequestBody String receiverUsername) {
        var sender = new User();
        sender.setUsername(senderUsername);
        service.sendFriendRequest(sender, receiverUsername);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/acceptFriendRequest")
    public ResponseEntity<Void> acceptFriendRequest(@RequestBody String senderUsername, @RequestBody String receiverUsername) {
        var receiver = new User();
        receiver.setUsername(receiverUsername);
        service.acceptFriendRequest(receiver, senderUsername);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/declineFriendRequest")
    public ResponseEntity<Void> declineFriendRequest(@RequestBody String senderUsername, @RequestBody String receiverUsername) {
        var receiver = new User();
        receiver.setUsername(receiverUsername);
        service.declineFriendRequest(receiver, senderUsername);
        return ResponseEntity.ok().build();
    }
}
