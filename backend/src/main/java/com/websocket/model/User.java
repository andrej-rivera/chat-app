package com.websocket.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

// Initialize annotation to generate boilerplate for User objects
@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    private Long id;
    private String username; // unique username for each user
    private String email; // unique email for each user (for signup)
    private String password; // hashed password
    private boolean enabled;
    private String verificationCode;
    private LocalDateTime verificationCodeExpiresAt;
    private Status status;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(UserData user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.enabled = user.isEnabled();
        this.verificationCode = user.getVerificationCode();
        this.verificationCodeExpiresAt = user.getVerificationCodeExpiresAt();
        this.status = user.getStatus();
    }


    // Override UserDetails Methods
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}   
