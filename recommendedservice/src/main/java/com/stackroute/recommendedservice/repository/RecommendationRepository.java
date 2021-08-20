package com.stackroute.recommendedservice.repository;

import com.stackroute.recommendedservice.entity.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, Integer> {

}
