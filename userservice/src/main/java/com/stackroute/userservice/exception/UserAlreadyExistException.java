package com.stackroute.userservice.exception;

public class UserAlreadyExistException extends RuntimeException {

    private String message;

    public UserAlreadyExistException(String message) {
        super(message);
        this.message = message;
    }

    public UserAlreadyExistException() {
    }


}
