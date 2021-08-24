package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.exception.UserNotFoundException;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.service.SearchEngineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class SearchEngineController {
//    private final String URL = "https://g.tenor.com/v1/search?q=";
//    //private final String query = "q=";
//    private final String LIMIT = "&limit=8";
//    @Value("${api.key}")
//    private String apiKey;
//    @Autowired
//    private RestTemplate restTemplate;
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
   // @GetMapping("/search?searchTerm={searchTerm}&userId={userId}")
//  test    http://localhost:8082/search?searchTerm=book
    public ResponseEntity<SearchEngine> getGifByName( @PathVariable(value="searchTerm") String searchTerm , @PathVariable(value="userId") int userId)throws UserNotFoundException{
      ResponseEntity<SearchEngine> result = new ResponseEntity<SearchEngine> (searchService.saveSearch(userId, searchTerm), HttpStatus.OK);
      log.info("Result is:  ");
      log.info(result.toString());
      searchService.getGifs();
       return result;






       // log.info("User id "searchService.getSearchInfoByUserId(userId).getSearchTerm());
//        ResponseEntity<String> response = restTemplate.getForEntity(searchURL , String.class);
//        HttpStatus statusCode = response.getStatusCode();
//        return response;
    }



}
