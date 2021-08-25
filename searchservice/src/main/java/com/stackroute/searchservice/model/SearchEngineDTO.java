package com.stackroute.searchservice.model;

public class SearchEngineDTO {
    private int userId;
    private String searchTerm;

    public SearchEngineDTO(){

    }

    public SearchEngineDTO(int userId, String searchTerm) {
        this.userId = userId;
        this.searchTerm = searchTerm;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
