package com.stackroute.searchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@JsonIgnoreProperties
@Data
@JsonRootName(value = "media")
public class Media {
    private Gif gif;

    public Media() {
    }
}
