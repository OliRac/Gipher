package com.stackroute.recommendedservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;
import java.util.Map;

@Document(collection = "userTerms")
public class UserTerms {
    private static final int threshold = 2;

    @Id
    private String id;
    @Indexed(unique = true)
    private int userId;
    private Map<String, Integer> terms;


    public UserTerms() {

    }

    public UserTerms(Map<String, Integer> terms, int userId) {
        this.terms = terms;
        this.userId = userId;
    }

    public void addTerm(String term) {
        int count = 1;

        if(terms.containsKey(term)) {
            count = terms.get(term) + 1;
        }

        terms.put(term, count);
    }

    public Map<String, Integer> getTerms() {
        return terms;
    }

    public void setTerms(Map<String, Integer> terms) {
        this.terms = terms;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserTerms{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", terms=" + terms +
                '}';
    }
}
