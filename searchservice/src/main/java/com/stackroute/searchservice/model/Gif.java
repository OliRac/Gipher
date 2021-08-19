package com.stackroute.searchservice.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
//to indicate that any properties not bound in this type should be ignored
@Document(collection="gifSearch")
@Data

public class Gif {
    private String type;
    private String id;
    private String title;
    private String embed_url;

    public Gif() {

    }

    public Gif(String gifID, String title, String url) {
        this.id = gifID;
        this.title = title;
        this.embed_url = url;
    }

    @Override
    public String toString() {
        return "Gif{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", embed_url='" + embed_url + '\'' +
                '}';
    }
}

