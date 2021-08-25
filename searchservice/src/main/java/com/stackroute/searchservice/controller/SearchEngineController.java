package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.SearchEngineDTO;
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


    @GetMapping("/gifs/{searchTerm}")
    public ResponseEntity<?>  getGif(@PathVariable(value="searchTerm") String searchTerm ){
      ResponseEntity<Object> result = new ResponseEntity<> (searchService.getGifs(searchTerm), HttpStatus.OK);
      return result;
    }

    @PostMapping("/search")
    public ResponseEntity<SearchEngine> saveSearch(@RequestBody SearchEngineDTO searchEngineDTO) {
      SearchEngine savedSearch = this.searchService.saveSearch(searchEngineDTO);
      return new ResponseEntity<SearchEngine> (savedSearch , HttpStatus.OK);
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
