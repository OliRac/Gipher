package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.entity.UserTermDTO;
import com.stackroute.recommendedservice.entity.UserTerms;
import com.stackroute.recommendedservice.exception.UserNotFoundException;
import com.stackroute.recommendedservice.repository.UserTermsRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
public class RecommendationServiceImpl implements RecommendationService, RabbitListenerConfigurer {
    private UserTermsRepository userTermsRepository;

    private static final int MAX_TRENDING_GIFS = 18;
    private static final int MAX_TERM_SUGGESTIONS = 2;
    private static final int MAX_SUGGESTIONS_PER_QUERY = 2;
    private static final int MAX_GIFS_PER_SUGGESTION = 3;
    private static final int NO_LIMIT = -1;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public RecommendationServiceImpl(UserTermsRepository userTermsRepository) {
        this.userTermsRepository = userTermsRepository;
    }

    @Override
    public UserTerms saveUserTerms(UserTerms userTerms) {
        return userTermsRepository.save(userTerms);
    }

    /*Adding a term to a users' collection of terms
    * If not already present, the userTerms is created and added*/
    @Override
    public String addTerm(int userId, String term) {
        UserTerms userTerms = userTermsRepository.findUserTermsByUserId(userId);

        if(userTerms == null) {
            userTerms = new UserTerms(new HashMap<String, Integer>(), userId);
        }

        userTerms.addTerm(term);
        userTermsRepository.save(userTerms);

        return term;
    }

    /*Gets trending gifs from the TENOR API. */
    @Override
    public String getTrending() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return queryTenor("trending?", HttpMethod.GET, headers, MAX_TRENDING_GIFS);
    }

    /*Current recommendation algorithm
    * Order the terms hashmap by increasing order of occurences
    * get the last couple of terms (2) and query the tenor API for search suggestions
    * take a couple of suggestions and perform searches for gifs with them
    * return the search resutls*/
    @Override
    public String getRecommendation(int userId) throws UserNotFoundException {
        UserTerms userTerms = userTermsRepository.findUserTermsByUserId(userId);

        if(userTerms == null) {
            throw new UserNotFoundException("There are no recommendations for the moment");
        }

        var sortedTerms = getSortedTerms(userTerms.getTerms());
        var termsToUse = getTermsToUse(sortedTerms, MAX_TERM_SUGGESTIONS);
        var suggestedSearchTerms = getSuggestedSearchTerms(termsToUse, MAX_SUGGESTIONS_PER_QUERY);

        var recommendations = new StringBuilder();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        recommendations.append("[");

        for (String term: suggestedSearchTerms) {
           String response = queryTenor("search?q=" + term, HttpMethod.GET, headers, MAX_GIFS_PER_SUGGESTION);
           recommendations.append(response + ",");
        }

        recommendations.replace(recommendations.length()-1, recommendations.length(), "]");

        return recommendations.toString();
    }

    @Override
    @CacheEvict(value="recommendationCache", key="#info.getUserId()")
    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveUserTermDTO(UserTermDTO info) {
        log.info("Got a UserTermDTO from another service: " + info.toString());
        String term = addTerm(info.getUserId(), info.getSearchTerm().toLowerCase());
        log.info("Added " + term + " to user " + info.getUserId() + "'s collection");
    }

    /*Helper to send queries to tenor*/
    public String queryTenor(String query, HttpMethod method, HttpHeaders headers, int limit) {
        var requestEntity = new HttpEntity<>(headers);
        String url = "https://g.tenor.com/v1/" + query + "&key=" + apiKey;

        if(limit > 0) {
            url += "&limit=" + limit;
        }

        var responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        return responseEntity.getBody();
    }

    /*Helper to sort a users term hashmap
    * Sorting with stream: get entryset, sort by comparing values, add to a linkedhashmap*/
    protected String[] getSortedTerms(Map<String, Integer> termsMap){
        return termsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(), (e1, e2) -> e2, LinkedHashMap::new)
                )
                .keySet()
                .toArray(new String[termsMap.size()]); //further converting to an array for easy access to last elements
    }

    /*From an array of sorted terms (ascending), this returns the last few, up to max*/
    protected List<String> getTermsToUse(String [] sortedTerms, int max) {
        //if its smaller we can just put everything as a term to use
        if(sortedTerms.length < max) {
            return Arrays.asList(sortedTerms);
        }

        var termsToUse = new LinkedList<String>();
        int lastIndex = sortedTerms.length - 1;

        for(int i = 0; i < max; i++) {
            termsToUse.add(sortedTerms[lastIndex - i]);
        }

        return termsToUse;
    }

    /*gets search suggestions from tenor out of the list of terms
    * takes up to MAX_SUGGESTIONS_PER_QUERY number of search suggestions per term*/
    protected List<String> getSuggestedSearchTerms(List<String> terms, int maxSuggestions) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        var searchTerms = new LinkedList<String>();

        for(String term: terms) {
            String body = queryTenor("search_suggestions?q=" + term, HttpMethod.GET, headers, NO_LIMIT);
            JSONArray arr = new JSONArray(new JSONObject(body).get("results").toString());

            for(int i = 0; i < maxSuggestions; i++) {
                if(arr.length() <= i) {
                    break;
                }
                searchTerms.add(arr.get(i).toString());
            }
        }

        return searchTerms;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {

    }
}
