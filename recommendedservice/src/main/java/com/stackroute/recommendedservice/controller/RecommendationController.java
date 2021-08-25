package com.stackroute.recommendedservice.controller;

import com.stackroute.recommendedservice.exception.UserDoesNotExistException;
import com.stackroute.recommendedservice.service.RecommendationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class RecommendationController {
    private RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    /*Responds with the term added to the user's dictionary of terms*/
    @PostMapping("/addTerm/{userId}/{term}")
    public ResponseEntity<?> addTerm(@PathVariable int userId, @PathVariable String term) {
        logRequest("post", "/addTerm/" + userId + "/" + term);
        var response = new ResponseEntity<String>(recommendationService.addTerm(userId, term), HttpStatus.OK);
        logResponseStatus(response.getStatusCode());
        return response;
    }

    /*Gets a users recommended gifs. Details on the algorithm in the recommendation service.
    * Responds with an array of JSON objects as strings [{},{}...]*/
    @GetMapping("/recommendation/{userId}")
    public ResponseEntity<?> getRecommendation(@PathVariable int userId) throws UserDoesNotExistException {
        logRequest("post", "/recommendation/" + userId);
        var response = new ResponseEntity<>(recommendationService.getRecommendation(userId), HttpStatus.OK);
        logResponseStatus(response.getStatusCode());
        return response;
    }

    /*Gets trending gifs from the Tenor API. */
    @GetMapping("/trending")
    public ResponseEntity<?> getTrending() {
        logRequest("get", "/trending");
        var response = new ResponseEntity<>(recommendationService.getTrending(), HttpStatus.OK);
        logResponseStatus(response.getStatusCode());
        return response;
    }

    /*To help logging*/
    private void logRequest(String method, String endpoint) {
        log.info(method.toUpperCase() + " /api/v1" + endpoint);
    }

    private void logRequest(String method, String endpoint, String params) {
        log.info(method.toUpperCase() + " /api/v1" + endpoint + " with params: " + params);
    }

    private void logResponseStatus(HttpStatus status) {
        log.info("Sending response with status " + status);
    }
}
