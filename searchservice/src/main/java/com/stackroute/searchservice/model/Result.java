package com.stackroute.searchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties
@Data
@JsonRootName(value = "results")
public class Result {
//    private String id;
//    private String title;
//   private List<Media> mediaList;
//    private String url;
   private Object[] objects;


    public Result() {
    }
}
