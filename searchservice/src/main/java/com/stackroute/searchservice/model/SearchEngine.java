package com.stackroute.searchservice.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Document(collection="searchHistory")
/*
 * Add annotation to indicate that object identity will be used during serialization.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = SearchEngine.class)
public class SearchEngine implements Serializable {
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

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public Set<String> getSearchTermSet() {
        return searchTermSet;
    }

    public void setSearchTermSet(Set<String> searchTermSet) {
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
