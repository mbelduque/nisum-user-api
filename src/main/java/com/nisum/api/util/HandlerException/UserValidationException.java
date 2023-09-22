package com.nisum.api.util.HandlerException;

public class UserValidationException extends RuntimeException {

    public UserValidationException(String message) {
        super(message);
    }
}