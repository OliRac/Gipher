package com.tackroute.favoriteservice.domain;


import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashSet;

@Document()
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
