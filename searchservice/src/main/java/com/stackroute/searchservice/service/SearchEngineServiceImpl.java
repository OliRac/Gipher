package com.stackroute.searchservice.service;

import com.stackroute.searchservice.exception.UserNotFoundException;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.UserTermDTO;
import com.stackroute.searchservice.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class SearchEngineServiceImpl implements SearchEngineService{

    /*
     * Add code to define RabbitTemplate
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private SearchRepository searchRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String URL = "https://g.tenor.com/v1/search?q=";
    private final String LIMIT = "&limit=9";

    @Value("${api.key:test}")
    private String apiKey;

    @Value("${spring.rabbitmq.exchange:test}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey:test}")
    private String routingkey;

    /*
     * Add code to autowire RabbitTemplate object using contructor autowiring
     */
    @Autowired
    public SearchEngineServiceImpl(RabbitTemplate rabbitTemplate, SearchRepository searchRepository){
        this.rabbitTemplate = rabbitTemplate;
        this.searchRepository = searchRepository;
    }

    @Override
    public SearchEngine saveSearch(UserTermDTO userTermDTO) throws UserNotFoundException {

        String searchURL = URL + userTermDTO.getSearchTerm() + "&key=" + apiKey + LIMIT;
        log.info("searchstring url:   " + searchURL);
        log.info("User id : " + userTermDTO.getUserId());

        SearchEngine searchInfo = searchRepository.findByUserId(userTermDTO.getUserId());
        if(searchInfo != null) {
            searchInfo.setSearchTerm(userTermDTO.getSearchTerm());
            log.info("User exists:  " + userTermDTO.getUserId() + "adding new searchTerm");
            searchInfo.getSearchTermSet().add(userTermDTO.getSearchTerm());
        }else{

            //Object gif = restTemplate.getForObject(searchURL , Gif.class);

           // log.info("Response from restTemplate:   " + gif.toString());
            Set<String> searchSet = new HashSet<String>();
            searchSet.add(userTermDTO.getSearchTerm());

            searchInfo = new SearchEngine(userTermDTO.getUserId() , searchSet);
            searchInfo.setSearchTerm(userTermDTO.getSearchTerm());

        }
        /*
         * Add code to publish the method to the exchange
         * with specified routingKey using the RabbitTemplate object.
         */
        if(userTermDTO.getUserId() != -1){
            log.info("Sending a UserTermDTO to RecommendationService");
            rabbitTemplate.convertAndSend(exchange, routingkey, searchInfo);
        }

        return searchRepository.save(searchInfo);

    }

    @Override
    public Object getGifs(String searchTerm){
        String searchURL = URL + searchTerm + "&key=" + apiKey + LIMIT;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(searchURL ,
                HttpMethod.GET ,
                requestEntity,
                String.class);
        HttpStatus statusCode = responseEntity.getStatusCode();
        log.info("HTTPStatus is:  " + statusCode);
        //Gif gif = responseEntity.getBody();
        //log.info("Response body-   "+ responseEntity.getBody());


        return responseEntity.getBody();
    }

    @Override
    public SearchEngine findByUserId(int userId) {
        SearchEngine search = null;
        search = searchRepository.findByUserId(userId);
        return search;

    }

    @Override
    public List<SearchEngine> getAllSearch() {
        return searchRepository.findAll();
    }

}
