package com.stackroute.searchservice.model;

public class Gif {
    private String gifID;
    private String title;
    private String gifUrl;

    public Gif() {

    }

    public Gif(String gifID, String title, String gifUrl) {
        this.gifID = gifID;
        this.title = title;
        this.gifUrl = gifUrl;
    }

    public String getGifID() {
        return gifID;
    }

    public void setGifID(String gifID) {
        this.gifID = gifID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }
}
