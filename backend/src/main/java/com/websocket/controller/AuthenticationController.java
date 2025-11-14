package com.websocket.controller;

import com.websocket.dto.LoginUserDto;
import com.websocket.dto.RegisterUserDto;
import com.websocket.dto.VerifyUserDto;
import com.websocket.model.User;
import com.websocket.model.UserData;
import com.websocket.responses.LoginResponse;
import com.websocket.service.AuthenticationService;
import com.websocket.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/auth")
@Controller
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    // Signup Endpoint
    @PostMapping("/signup") // "/auth/signup"
    public ResponseEntity<?> register(@RequestBody RegisterUserDto input) {
        try {
            UserData entity = authenticationService.signup(input);
            return ResponseEntity.ok(entity);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    // Login Endpoint
    @PostMapping("/login") // "/auth/login"
    public ResponseEntity<?> authenticate(@RequestBody LoginUserDto input) {
        try {
            UserData entity = authenticationService.authenticate(input);
            User user = new User(entity);

            String jwtToken = jwtService.generateToken(user);
            LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime());
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyUserDto input) {
        try {
            authenticationService.verifyUser(input);
            return ResponseEntity.ok("Account associated with " + input.getEmail() +  " verified successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {
        try {
            authenticationService.resendVerificationCode(email);
            return ResponseEntity.ok("Verification Code Sent");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
