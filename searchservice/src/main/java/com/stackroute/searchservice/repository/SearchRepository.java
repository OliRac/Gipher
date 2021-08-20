package com.stackroute.searchservice.repository;

import com.stackroute.searchservice.model.SearchEngine;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SearchRepository extends MongoRepository<SearchEngine, Integer> {
}
