package com.stackroute.searchservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.searchservice.model.Gif;
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
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search")
//    test http://localhost:8082/search?searchTerm=book
    public ResponseEntity<String> getGifByName(@RequestParam(value="searchTerm")  String searchTerm) throws JsonProcessingException {
      //  String searchSrt= URL + "api_key=" + apiKey + query + searchTerm;
        String searchURL = URL + searchTerm + "&key=" + apiKey;
        log.info("searchstring url:   " + searchURL);
//        Object obj = restTemplate.getForObject(searchSrt, String.class);
//        log.info("Is the object: " + (obj==null));
//        return new ObjectMapper().writeValueAsString(obj);
        ResponseEntity<String> response = restTemplate.getForEntity(searchURL , String.class);
        return response;
    }

}
