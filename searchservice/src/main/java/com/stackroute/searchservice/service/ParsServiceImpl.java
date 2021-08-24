package com.stackroute.searchservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ParsServiceImpl implements ParsService{
    @Autowired
    private RestTemplate restTemplate;
    /**
     *
     * @param url
     * @return
     */
    @Override
    public Object parse(String url) {
        return restTemplate.getForObject(url, Object.class);
    }
}
