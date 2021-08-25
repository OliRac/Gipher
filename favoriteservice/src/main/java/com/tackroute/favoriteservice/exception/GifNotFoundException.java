package com.tackroute.favoriteservice.exception;

public class GifNotFoundException extends RuntimeException {
    private String message ;

    public GifNotFoundException() {
    }

    public GifNotFoundException(String message) {
        super();
        this.message = message;
    }
}
