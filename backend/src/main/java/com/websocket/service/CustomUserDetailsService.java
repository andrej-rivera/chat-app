package com.websocket.service;

import com.websocket.model.User;
import com.websocket.model.UserData;
import com.websocket.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        // obtain user entity from database
        UserData userEntity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new User(userEntity);
    }

    public User loadUserByEmail(String email) throws UsernameNotFoundException {
        // obtain user entity from database
        UserData userEntity = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        return new User(userEntity);
    }

    public static UserData convertUserIntoEntity(User user) {
        return UserData.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .status(user.getStatus())
                .enabled(user.isEnabled())
                .verificationCode(user.getVerificationCode())
                .verificationCodeExpiresAt(user.getVerificationCodeExpiresAt())
                .build();
    }





    public CustomUserDetailsService(
            UserRepository userRepository
    ) {
        this.repository = userRepository;
    }
}
