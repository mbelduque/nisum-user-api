package com.nisum.api.util.HandlerException;

public class UserConflictException extends RuntimeException {
    public UserConflictException(String message) {
        super(message);
    }
}
