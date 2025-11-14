package com.websocket.dto;

import lombok.Getter;
import lombok.Setter;


// Data Transfer Object for creating users
@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String username;
    private String password;

}
