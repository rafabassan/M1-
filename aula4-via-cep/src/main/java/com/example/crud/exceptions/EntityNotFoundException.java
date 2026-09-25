package com.example.crud.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) { super(message); }
}
