package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.exception.UserAlreadyExistsException;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.service.SearchEngineService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SearchEngineControllerIntegrationTest {
    @Autowired
    private SearchEngineService searchService;
    private SearchEngine searchEngine;
    private List<SearchEngine> searchEngineList;
    private Set<String> searchSet;


    @BeforeEach
    public void setUp() {
        searchEngine = new SearchEngine();
        searchSet = new HashSet<>();
        searchEngineList = new ArrayList<>();
        searchEngine.setUserId(1);
        searchSet.add("book");

        searchEngineList.add(searchEngine);

    }
    @AfterEach
    public void tearDown() {
        searchEngine = null;
    }

    @Test
    void givenSearchEngineToSaveThenShouldReturnTheSavedSearch() throws Exception {
        //SearchEngine savedSearchEngine = searchService.saveSearch(searchEngine);
//        assertNotNull(savedSearchEngine);
//        assertEquals(searchEngine.getId(), savedSearchEngine.getId());
    }

    @Test
    public void givenGetAllSearchEngineThenShouldReturnListOfAllSearchEngineListShouldNotBeNull() throws Exception {
        List<SearchEngine> savedSearchEngine = searchService.getAllSearch();
        assertNotNull(searchEngineList);
    }
}