package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;

@Service
public class RecommendationServiceImpl implements RecommendationService{
    private RecommendationRepository recommendationRepository;

    private final int TRENDING_LIMIT = 10;

    @Value("${api.key}")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public RecommendationServiceImpl(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public String getTrending() {
        //String url = "https://g.tenor.com/v1/trending?key=" + apiKey + "&limit=+" + TRENDING_LIMIT;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //var requestEntity = new HttpEntity<>(headers);
        //var responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

        return queryTenor("trending?", HttpMethod.GET, headers);
    }

    public String queryTenor(String query, HttpMethod method, HttpHeaders headers) {
        var requestEntity = new HttpEntity<>(headers);
        String url = "https://g.tenor.com/v1/" + query + "key=" + apiKey; //trending?key="
        var responseEntity = restTemplate.exchange(url, method, requestEntity, String.class);
        return responseEntity.getBody();
    }
}
