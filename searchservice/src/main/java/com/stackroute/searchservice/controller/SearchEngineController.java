package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.service.SearchEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class SearchEngineController {
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
  @GetMapping("/searchTerm/{searchTerm}/userId/{userId}")
    //  test    http://localhost:8082/search?searchTerm=book
    public ResponseEntity<SearchEngine> getGifByName( @PathVariable(value="searchTerm") String searchTerm , @PathVariable(value="userId") int userId){
      ResponseEntity<SearchEngine> result = new ResponseEntity<SearchEngine> (searchService.saveSearch(userId, searchTerm), HttpStatus.OK);
       return result;
    }

    @GetMapping("/gifs/{searchTerm}")
    public ResponseEntity<?>  getGif(@PathVariable(value="searchTerm") String searchTerm ){
      ResponseEntity<Object> result = new ResponseEntity<> (searchService.getGifs(searchTerm), HttpStatus.OK);
      return result;
    }
    @PostMapping("/search")
    public ResponseEntity<SearchEngine> saveSearch(@RequestBody SearchEngine search) {
      SearchEngine savedSearch = this.searchService.saveSearch(search);
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
