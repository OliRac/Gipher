package com.tackroute.favoriteservice.repository;

import com.tackroute.favoriteservice.model.Selection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface FavoriteRepository extends MongoRepository<Selection, Integer> {

}
