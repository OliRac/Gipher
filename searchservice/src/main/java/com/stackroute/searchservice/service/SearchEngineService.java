package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;

import java.util.List;
import java.util.Optional;

public interface SearchEngineService {
    SearchEngine saveSearch(int userId, String searchTerm) ;
//    List<SearchEngine> getAllSearch();
//    SearchEngine updateSearchInfo(SearchEngine searchEngine);
    SearchEngine findSearchByUserId(int userId);
    void getGifs();
}
