package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationServiceImpl implements RecommendationService{
    private RecommendationRepository recommendationRepository;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }
}
