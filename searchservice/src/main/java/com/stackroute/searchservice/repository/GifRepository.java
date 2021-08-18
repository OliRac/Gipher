package com.stackroute.searchservice.repository;

import com.stackroute.searchservice.model.Gif;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GifRepository extends MongoRepository<Gif, String> {
    List<Gif> findByTitle(String title);
}
