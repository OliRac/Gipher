package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@CrossOrigin
@RestController
@RequestMapping("https://api.giphy.com/v1/gifs/search?")
public class SearchEngineController {
    private final String apiUrl = "https://api.giphy.com/v1/gifs/search?";
    private final int limit = 1;
    @Value("${api.key}")
    private String apiKey;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public ResponseEntity<?> getGifByName(@PathVariable("q") String searchTerm){
        return restTemplate.getForEntity(apiUrl + apiKey + "&limit=" + limit + "&q=" +searchTerm, Gif.class);

    }

}
