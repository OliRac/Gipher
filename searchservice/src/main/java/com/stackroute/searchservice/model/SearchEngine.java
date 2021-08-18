package com.stackroute.searchservice.model;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;


public class SearchEngine {
    private int userID;
    private Set<String> searchTerm = new HashSet<String>();

    public SearchEngine() {
    }

    public SearchEngine(int userID, Set<String> searchTerm) {
        this.userID = userID;
        this.searchTerm = searchTerm;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Set<String> getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(Set<String> searchTerm) {
        this.searchTerm = searchTerm;
    }

    @Override
    public String toString() {
        return "SearchEngine{" +
                "userID=" + userID +
                ", searchTerm=" + searchTerm +
                '}';
    }
}
