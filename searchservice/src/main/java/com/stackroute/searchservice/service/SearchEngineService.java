package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;

import java.util.List;
import java.util.Optional;

public interface SearchEngineService {
    SearchEngine addSearchInfo(int userId, String searchTerm) ;
    List<SearchEngine> getAllSearch();
    SearchEngine updateSearchInfo(SearchEngine searchEngine);

}
