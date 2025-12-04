package com.example.springBoot_ecommerce_backend.Exception;

public class AlreadyExistsException extends RuntimeException {
    
    public AlreadyExistsException(String message) {
        super(message);
    }
}
