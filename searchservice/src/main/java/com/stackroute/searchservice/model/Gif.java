package com.stackroute.searchservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Collections;

@Document(collection="gifSearch")

public class Gif {
    @Id
    private String id;
    private String title;
    private String url;

    public Gif() {

    }

    public Gif(String gifID, String title, String url) {
        this.id = gifID;
        this.title = title;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
