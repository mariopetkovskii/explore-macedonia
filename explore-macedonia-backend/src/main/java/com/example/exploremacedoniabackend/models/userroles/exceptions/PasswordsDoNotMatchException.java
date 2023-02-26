package com.example.exploremacedoniabackend.models.userroles.exceptions;

public class PasswordsDoNotMatchException extends RuntimeException{
    public PasswordsDoNotMatchException() {
        super("Password do not match!");
    }
}