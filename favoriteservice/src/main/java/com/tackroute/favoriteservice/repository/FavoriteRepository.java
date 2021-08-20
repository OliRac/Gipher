package com.tackroute.favoriteservice.repository;

import com.tackroute.favoriteservice.domain.Selection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends MongoRepository<Selection, Integer> {
    Selection findByUserId(int userId);
    boolean existsByUserId(int userId);
}
