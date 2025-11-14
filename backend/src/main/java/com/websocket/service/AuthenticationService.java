package com.websocket.service;

import com.websocket.dto.LoginUserDto;
import com.websocket.dto.RegisterUserDto;
import com.websocket.dto.VerifyUserDto;
import com.websocket.model.Status;
import com.websocket.model.User;
import com.websocket.model.UserData;
import com.websocket.repository.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

import java.time.LocalDateTime;

@Service
public class AuthenticationService{
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    private final CustomUserDetailsService userDetailsService;


    public AuthenticationService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            EmailService emailService,
            CustomUserDetailsService userDetailsService
    ) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
        this.userDetailsService = userDetailsService;
    }

    public UserData signup(RegisterUserDto input) {
        if (repository.findByEmail(input.getEmail()).isPresent()) {
            resendVerificationCode(input.getEmail());
            throw new RuntimeException("User already exists, sending new verification code");
        }

        User user = new User(input.getUsername(), input.getEmail(), passwordEncoder.encode(input.getPassword()));
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        user.setEnabled(false);
        sendVerificationEmail(user);

        UserData userEntity = userDetailsService.convertUserIntoEntity(user);
        return repository.save(userEntity);
    }

    // For login
    public UserData authenticate(LoginUserDto input) {
        User user = userDetailsService.loadUserByUsername(input.getUsername());

        // If account not verified, throw error
        if (!user.isEnabled()) {
            throw new RuntimeException("Account not verified.");
        }

        // Authenticate with username and password
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                        ));
        
        // If authentication succeeds, fetch and return the entity
        UserData entity = repository.findByUsername(input.getUsername())
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return entity;
    }

    public void verifyUser(VerifyUserDto input) {
        User user = userDetailsService.loadUserByEmail(input.getEmail());

        if (user.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Verification Code has Expired");
        }

        // if valid verification code, enable account
        if (user.getVerificationCode().equals(input.getVerification())) {
            user.setEnabled(true);
            user.setStatus(Status.ONLINE);
            user.setVerificationCode(null);
            user.setVerificationCodeExpiresAt(null);
            UserData userEntity = userDetailsService.convertUserIntoEntity(user);
            repository.save(userEntity);
        } else {
            throw new RuntimeException("Invalid Verification Code");
        }
    }

    public void resendVerificationCode(String email) {
        User user = userDetailsService.loadUserByEmail(email);

        if (user.isEnabled()) {
            throw new RuntimeException("Account already verified");
        }
        user.setVerificationCode(generateVerificationCode());
        user.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
        sendVerificationEmail(user);

        UserData userEntity = userDetailsService.convertUserIntoEntity(user);
        repository.save(userEntity);
    }

    public void sendVerificationEmail(User user) {
        String subject = "Account Verification";
        String verificationCode = user.getVerificationCode();
        String message = "Welcome to my Chat WebApp! \n"
        + "\n Your verification code is: " + verificationCode;

        try {
            emailService.sendVerificationEmail(user.getEmail(), subject, message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }




}
