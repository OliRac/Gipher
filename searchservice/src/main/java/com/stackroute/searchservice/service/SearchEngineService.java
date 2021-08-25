package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.SearchEngineDTO;

import java.util.List;
import java.util.Optional;

public interface SearchEngineService {
    SearchEngine saveSearch(SearchEngineDTO searchEngineDTO) ;
    Object getGifs(String searchTerm);
    SearchEngine findByUserId(int userId);

    List<SearchEngine> getAllSearch();
}
