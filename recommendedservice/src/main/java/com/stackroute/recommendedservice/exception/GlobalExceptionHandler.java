package com.stackroute.recommendedservice.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleBlogNotFound(UserNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        log.info("Sending response with status " + status + ": User not found");
        return new ResponseEntity<>(ex.getMessage(), status);
    }

}
