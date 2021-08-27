package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.UserTermDTO;

import java.util.List;

public interface SearchEngineService {
    SearchEngine saveSearch(UserTermDTO userTermDTO) ;
    Object getGifs(String searchTerm);
    SearchEngine findByUserId(int userId);
    List<SearchEngine> getAllSearch();
}
