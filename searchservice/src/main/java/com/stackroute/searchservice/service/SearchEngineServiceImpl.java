package com.stackroute.searchservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.searchservice.exception.UserNotFoundException;
import com.stackroute.searchservice.model.Gif;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.h2.util.json.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SearchEngineServiceImpl implements SearchEngineService{
    @Autowired
    SearchRepository searchRepository;
    @Autowired
    private RestTemplate restTemplate;
    private final String URL = "https://g.tenor.com/v1/search?q=";
    private final String LIMIT = "&limit=1";
    @Value("${api.key}")
    private String apiKey;

    public SearchEngineServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    /***
     * Saves the searchEngine object into db.
     * It checks if the searchEngine with the give userId exists. If exists it will add the searchTerm into the existing set,
     * else it creates a new set.
     * @param userId
     * @param searchTerm
     * @return searchEngine object
     */
    @Override
    public SearchEngine saveSearch(int userId, String searchTerm) throws UserNotFoundException {

        String searchURL = URL + searchTerm + "&key=" + apiKey + LIMIT;
        log.info("searchstring url:   " + searchURL);
        log.info("User id : " + userId);

        SearchEngine searchInfo = searchRepository.findByUserId(userId);
//        searchInfo.setSearchTerm(searchTerm);
        if(searchInfo != null) {
            searchInfo.setSearchTerm(searchTerm);
            log.info("User exists:  " + userId + "adding new searchTerm");
//            String myURL = URL + searchTerm +"&userId="+userId+ "&key=" + apiKey + LIMIT;
//            String response = restTemplate.getForObject(searchURL , String.class);
//            log.info("Response from restTemplate:   " + response.toString());
            searchInfo.getSearchTermSet().add(searchTerm);
        }else{

            //Object gif = restTemplate.getForObject(searchURL , Gif.class);

           // log.info("Response from restTemplate:   " + gif.toString());
            Set<String> searchSet = new HashSet<String>();
            searchSet.add(searchTerm);
            searchInfo = new SearchEngine(userId , searchSet);
            searchInfo.setSearchTerm(searchTerm);
        }
        getGifs(searchTerm);

        return searchRepository.save(searchInfo);

    }

    @Override
    public void getGifs(String searchTerm){
        String searchURL = URL + searchTerm + "&key=" + apiKey + LIMIT;
//        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(searchURL, Object[].class);
//        Object[] objects = responseEntity.getBody();
//            ObjectMapper mapper = new ObjectMapper();
//            return Arrays.stream(objects)
//                    .map(object -> mapper.convertValue(object, Gif.class))
//                    .map(Gif::getUrl)
//                    .collect(Collectors.toList());
//
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Gif> responseEntity = restTemplate.exchange(searchURL ,
                HttpMethod.GET ,
                requestEntity,
                Gif.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        log.info("HTTPStatus is:  " + statusCode);
        Gif gif = responseEntity.getBody();
        log.info("Response body-   "+ gif);


    }

}
