package com.stackroute.userservice.exception;

public class InvalidPasswordException extends RuntimeException {
    private String message;

    public InvalidPasswordException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidPasswordException() {
    }
}
