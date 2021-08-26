package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.entity.UserTerms;
import com.stackroute.recommendedservice.exception.UserNotFoundException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public interface RecommendationService {
    String getTrending();
    String getRecommendation(int userId) throws UserNotFoundException;
    String addTerm(int userId, String term);
    UserTerms saveUserTerms(UserTerms userTerms);
}
