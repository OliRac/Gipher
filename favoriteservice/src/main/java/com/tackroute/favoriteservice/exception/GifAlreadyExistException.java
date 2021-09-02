package com.tackroute.favoriteservice.exception;

public class GifAlreadyExistException extends RuntimeException{
    private String message ;

    public GifAlreadyExistException(){

    }

    public GifAlreadyExistException(String message) {
        super();
        this.message = message;

    }
}
