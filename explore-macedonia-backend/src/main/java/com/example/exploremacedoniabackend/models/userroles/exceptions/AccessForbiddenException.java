package com.example.exploremacedoniabackend.models.userroles.exceptions;

public class AccessForbiddenException extends RuntimeException{
    public AccessForbiddenException() {
        super("Access forbidden");
    }
}