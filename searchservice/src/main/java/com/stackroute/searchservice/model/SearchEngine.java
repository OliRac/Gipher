package com.stackroute.searchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Document(collection="searchHistory")
public class SearchEngine {
    @Id
    private String id;
    private int userId;
    private String searchTerm;
    private Set<String> searchTermSet = new HashSet<String>();

    public SearchEngine() {
    }

    public SearchEngine(int userId, Set<String> searchTermSet) {
        this.userId = userId;
        this.searchTermSet = searchTermSet;
    }

    @Override
    public String toString() {
        return "SearchEngine{" +
                "userID=" + userId +
                ", searchTermSet =" + searchTermSet +

                '}';
    }
}
