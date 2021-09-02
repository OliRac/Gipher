package com.tackroute.favoriteservice.exception;

public class NoFavoriteGifFoundException extends RuntimeException{
    String message ;

    public NoFavoriteGifFoundException() {
    }

    public NoFavoriteGifFoundException(String message) {
       super();
        this.message = message;
    }
}
