package com.stackroute.recommendedservice.repository;

import com.stackroute.recommendedservice.entity.UserTerms;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTermsRepository extends MongoRepository<UserTerms, Integer> {
    UserTerms findUserTermsByUserId(int userId);
}
