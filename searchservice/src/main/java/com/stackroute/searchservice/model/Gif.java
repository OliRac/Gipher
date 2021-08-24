package com.stackroute.searchservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
//to indicate that any properties not bound in this type should be ignored
//@Document(collection="gifSearch")
@Data
//@JsonDeserialize(as = Gif.class)
@JsonRootName(value = "gif")
public class Gif {
  //  @JsonProperty("results[0].media[0].gif.url")
    private String url;
    private Object[] results;

    public Gif() {

    }

  @Override
  public String toString() {
    return "Gif{" +
            "results=" + Arrays.toString(results) +
            '}';
  }
}

