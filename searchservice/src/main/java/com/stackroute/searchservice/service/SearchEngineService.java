package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;

import java.util.List;
import java.util.Optional;

public interface SearchEngineService {
    SearchEngine saveSearch(int userId, String searchTerm) ;
    void getGifs(String searchTerm);
}
