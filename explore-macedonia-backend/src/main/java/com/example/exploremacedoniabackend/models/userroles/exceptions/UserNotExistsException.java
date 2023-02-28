package com.example.exploremacedoniabackend.models.userroles.exceptions;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException() {
        super("User not exists");
    }
}
