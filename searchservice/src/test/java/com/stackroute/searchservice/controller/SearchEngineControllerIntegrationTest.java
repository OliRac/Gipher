package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.UserTermDTO;
import com.stackroute.searchservice.service.SearchEngineService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SearchEngineControllerIntegrationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SearchEngineService searchService;

    private UserTermDTO userTermDTO;
    private SearchEngine searchEngine;
    private List<UserTermDTO> searchEngineList;
    private Set<String> searchSet;


    @BeforeEach
    public void setUp() {
        userTermDTO = new UserTermDTO();
        searchSet = new HashSet<>();
        searchEngineList = new ArrayList<>();
        userTermDTO.setUserId(-1);
        userTermDTO.setSearchTerm("book");

        searchEngine = new SearchEngine();


    }
    @AfterEach
    public void tearDown() {
        userTermDTO = null;
    }

    @Test
    void givenSearchEngineDTOToSaveThenShouldReturnTheSavedSearchEngine() throws Exception {
        SearchEngine savedSearchEngine = searchService.saveSearch(userTermDTO);
        assertNotNull(savedSearchEngine);
        assertEquals(userTermDTO.getUserId(), savedSearchEngine.getUserId());
    }

    @Test
    public void givenGetAllSearchEngineThenShouldReturnListOfAllSearchEngineListShouldNotBeNull() throws Exception {
        List<SearchEngine> savedSearchEngine = searchService.getAllSearch();
        assertNotNull(searchEngineList);
    }
}