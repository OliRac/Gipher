package com.stackroute.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> blogAlreadyExistsException(UserAlreadyExistException userAlreadyExistException){
        return new ResponseEntity<String>("User Already Exists", HttpStatus.CONFLICT);
    }
}
