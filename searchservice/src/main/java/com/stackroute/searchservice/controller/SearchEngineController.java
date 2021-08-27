package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.UserTermDTO;
import com.stackroute.searchservice.service.SearchEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/search-service")
public class SearchEngineController {


    @Autowired
    private RestTemplate restTemplate;
    @Autowired

    private SearchEngineService searchService;

    @Autowired
    private SearchEngine searchEngine;


    @PostMapping("/gifs/search")
    public ResponseEntity<?>  getGif(@RequestBody UserTermDTO userTerm){
      ResponseEntity<Object> result = new ResponseEntity<> (searchService.getGifs(userTerm.getSearchTerm()), HttpStatus.OK);
      this.searchService.saveSearch(userTerm);
      return result;
    }

    @GetMapping("/searches")
    public ResponseEntity<List<SearchEngine>> getAllSearches() {
        return new ResponseEntity<List<SearchEngine>>((List<SearchEngine>) this.searchService.getAllSearch(), HttpStatus.OK);
    }

    @GetMapping("/search/{userId}")
    public ResponseEntity<SearchEngine> getSearchByUserId(@PathVariable("userId") int userId) {
        return new ResponseEntity<SearchEngine>(this.searchService.findByUserId(userId), HttpStatus.FOUND);
    }
}
