package com.example.exploremacedoniabackend.models.places.exceptions;

public class LocationNotFoundException extends RuntimeException{
    public LocationNotFoundException() {
        super("Location was not found");
    }
}
