package com.tackroute.favoriteservice.exception;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(getClass());


    @Value(value = "${data.exception.message1}")
    String message1;

    @Value(value = "${data.exception.message2}")
    String message2;

    @Value(value = "${data.exception.message3}")
    String message3;


    @ExceptionHandler(value = GifAlreadyExistException.class)
    public ResponseEntity<String> GifAlreadyExistException(GifAlreadyExistException gifAlreadyExistException) {
        LOG.error(message1, HttpStatus.CONFLICT);
        return new ResponseEntity<>(message1, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = GifNotFoundException.class)
    public ResponseEntity<String> GifNotFoundException(GifNotFoundException gifNotFoundException) {
        LOG.error(message2, HttpStatus.CONFLICT);
        return new ResponseEntity<>(message2, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NoFavoriteGifFoundException.class)
    public ResponseEntity<String> NoFavoriteGifFoundException(NoFavoriteGifFoundException noFavoriteGifFoundException) {
        LOG.error(message3, HttpStatus.CONFLICT);
        return new ResponseEntity<>(message3, HttpStatus.NOT_FOUND);
    }

}
