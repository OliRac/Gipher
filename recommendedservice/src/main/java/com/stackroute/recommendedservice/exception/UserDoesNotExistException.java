package com.stackroute.recommendedservice.exception;

public class UserDoesNotExistException extends RuntimeException{
    public UserDoesNotExistException(String message) {
        super(message);
    }

    public UserDoesNotExistException() {

    }
}
