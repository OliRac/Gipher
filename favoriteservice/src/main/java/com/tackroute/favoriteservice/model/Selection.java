package com.tackroute.favoriteservice.model;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "selections")
public class Selection {

    @Id
    private int userId;
    private HashSet<String> favoriteList = new HashSet<String>();

    public Selection() {
    }

    public Selection(int userId,  HashSet<String> favoriteList) {
        this.userId = userId;
        this.favoriteList = favoriteList;
    }



    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HashSet<String> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(HashSet<String> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void addFavoriteItem(String GifUrl){
        this.favoriteList.add(GifUrl);
    }

    public void removeFavoriteItem(String GifUrl){
        this.favoriteList.remove(GifUrl);
    }

    public void emptyFavoriteList(){
        this.favoriteList.clear();
    }

}
