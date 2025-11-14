package com.websocket.controller;

import com.websocket.config.SecurityConfiguration;
import com.websocket.model.User;
import com.websocket.model.UserData;
import com.websocket.service.CustomUserDetailsService;
import com.websocket.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/users")
@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserData> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserData entity = CustomUserDetailsService.convertUserIntoEntity(currentUser);
        return ResponseEntity.ok(entity);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserData>> allUsers() {
        List<UserData> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }
}
