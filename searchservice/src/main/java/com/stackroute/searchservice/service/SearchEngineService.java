package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;

import java.util.List;
import java.util.Optional;

public interface SearchEngineService {
    SearchEngine saveSearch(int userId, String searchTerm) ;
    Object getGifs(String searchTerm);
    SearchEngine findByUserId(int userId);

    SearchEngine saveSearch(SearchEngine search);

    List<SearchEngine> getAllSearch();
}
