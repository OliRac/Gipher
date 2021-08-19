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
    private final String apiUrl = "https://api.giphy.com/v1/gifs/search?";
    private final String query = "&q=";
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/search")
//    test http://localhost:8082/search?searchTerm=book
    public String getGifByName(@RequestParam(value="searchTerm")  String searchTerm) throws JsonProcessingException {
        String searchSrt= apiUrl + "api_key=" + apiKey + query + searchTerm;
        log.info("searchstring url:   " + searchSrt);
        Object obj = restTemplate.getForObject(searchSrt, Gif.class);
        return new ObjectMapper().writeValueAsString(obj);

    }

}
