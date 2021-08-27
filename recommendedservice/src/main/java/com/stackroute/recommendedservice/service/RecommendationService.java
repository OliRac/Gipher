package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.entity.UserTermDTO;
import com.stackroute.recommendedservice.entity.UserTerms;
import com.stackroute.recommendedservice.exception.UserNotFoundException;

public interface RecommendationService {
    String getTrending();
    String getRecommendation(int userId) throws UserNotFoundException;
    String addTerm(int userId, String term);
    UserTerms saveUserTerms(UserTerms userTerms);
    void receiveUserTermDTO(UserTermDTO info);
}
