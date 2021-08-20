package com.tackroute.favoriteservice.domain;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.annotation.processing.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.HashSet;

@Document(collection = "selection")
public class Selection {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private int userId;
    private HashSet<String> favoriteList = new HashSet<String>();

    public Selection() {
    }

    public Selection(int userId, HashSet<String> favoriteList) {
        this.userId = userId;
        this.favoriteList = favoriteList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
