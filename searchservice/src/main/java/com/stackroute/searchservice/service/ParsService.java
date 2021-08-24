package com.stackroute.searchservice.service;

public interface ParsService {
    /**
     * Service to parse the Json response and convert it to the desired moder
     */
    Object parse(String url);
}
