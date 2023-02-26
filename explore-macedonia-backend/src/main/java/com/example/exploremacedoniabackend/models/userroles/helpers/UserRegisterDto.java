package com.example.exploremacedoniabackend.models.userroles.helpers;

import lombok.Getter;

@Getter
public class UserRegisterDto {
    String firstName;
    String lastName;
    String email;
    String password;
    String confirmPassword;
}