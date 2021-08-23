package com.tackroute.favoriteservice.repository;

import com.tackroute.favoriteservice.domain.Selection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepository extends MongoRepository<Selection, String> {

//    @Query(value = "{'userId': ?0}")
    Selection findByUserId(int userId);

    @Query(value = "{'userId': ?0, 'favoriteList': {$all : ?1 }} ")
    String findByUserIdAndGifUrl(int userId, String gifUrl);

    boolean existsByUserId(int userId);

    void deleteByUserId(int userId);
}
