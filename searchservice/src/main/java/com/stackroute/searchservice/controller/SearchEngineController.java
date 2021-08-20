package com.stackroute.searchservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.searchservice.exception.UserNotFoundException;
import com.stackroute.searchservice.model.Gif;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.repository.SearchRepository;
import com.stackroute.searchservice.service.SearchEngineService;
import com.stackroute.searchservice.service.SearchEngineServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@Slf4j
@RestController

public class SearchEngineController {
//    private final String apiUrl = "https://api.giphy.com/v1/gifs/search?";
    private final String URL = "https://g.tenor.com/v1/search?q=";
    //private final String query = "q=";
    private final String LIMIT = "&limit=8";
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SearchEngineService searchService;
    @Autowired
    private SearchEngine searchEngine;

    /***
     * Calls to the external api. It gets the userId for searchEngine.
     * It has a default userId if the user is not the registered one so they can still search,
     * but if they are registered the search term will be stored in the searchEngine db
     * @param searchTerm
     * @param userId
     * @return ResponseEntity type string
     */
    @GetMapping("/search")
//  test    http://localhost:8082/search?searchTerm=book
    public ResponseEntity<String> getGifByName( @RequestParam(value="searchTerm") String searchTerm , @RequestParam(defaultValue = "-1") int userId)throws UserNotFoundException{

        String searchURL = URL + searchTerm + "&key=" + apiKey + LIMIT;
        log.info("searchstring url:   " + searchURL);
        log.info("User id : " + userId);
        searchService.addSearchInfo(userId, searchTerm);


       // log.info("User id "searchService.getSearchInfoByUserId(userId).getSearchTerm());
        ResponseEntity<String> response = restTemplate.getForEntity(searchURL , String.class);
        return response;
    }

}
