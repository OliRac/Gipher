package com.stackroute.recommendedservice.entity;

public class UserTermDTO {
    private int userId;
    private String searchTerm;

    public UserTermDTO(){

    }

    public UserTermDTO(int userId, String searchTerm) {
        this.userId = userId;
        this.searchTerm = searchTerm;
    }

    @Override
    public String toString() {
        return "SearchEngineDTO{" +
                "userId=" + userId +
                ", searchTerm='" + searchTerm + '\'' +
                '}';
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
