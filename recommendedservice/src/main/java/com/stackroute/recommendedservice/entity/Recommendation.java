package com.stackroute.recommendedservice.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

@Document(collection = "recommendation")
public class Recommendation {
    private HashMap<String, Integer> tags;
    private int userID;
    private static final int threshold = 2;
}
