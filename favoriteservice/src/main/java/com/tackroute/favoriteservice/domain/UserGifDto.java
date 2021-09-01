package com.tackroute.favoriteservice.domain;

public class UserGifDto {
     private int userId;
     private String gifUrl;

    public UserGifDto() {
    }

    public UserGifDto(int userId, String gifUrl) {
        this.userId = userId;
        this.gifUrl = gifUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
