package com.example.exploremacedoniabackend.models.userroles.exceptions;

public class UserNotEnabledException extends RuntimeException{
    public UserNotEnabledException() {
        super(String.format("User not enabled!"));
    }
}