package com.stackroute.searchservice.repository;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.service.SearchEngineService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SearchRepositoryIT {
    @Autowired
    private SearchRepository searchRepo;
    private SearchEngine searchEngine;
    private List<SearchEngine> searchEngineList;
    private Set<String> searchSet;


    @BeforeEach
    public void setUp() {
        searchEngine = new SearchEngine();
        searchSet = new HashSet<>();
        searchEngineList = new ArrayList<>();
        searchEngine.setUserId(-1);
        searchEngine.setId("a");
        searchSet.add("book");

        searchEngineList.add(searchEngine);

    }
    @AfterEach
    public void tearDown() {
        searchRepo.deleteAll();
        searchEngine = null;
    }
    @Test
    public void givenSearchToSaveThenShouldReturnSavedSearch() {
        searchRepo.save(searchEngine);
        SearchEngine fetchSearch = searchRepo.findById(searchEngine.getId()).get();
        assertEquals("a", fetchSearch.getId());
    }
    @Test
    public void givenGetAllBlogsThenShouldReturnListOfAllBlogs() {
        Set<String> searchSet1 = new HashSet<>();
        searchSet1.add("a");
        searchSet1.add("b");
        SearchEngine searchEngine = new SearchEngine();
        searchEngine.setUserId(1);
        searchEngine.setSearchTermSet(searchSet1);
        searchRepo.save(searchEngine);

        SearchEngine searchEngine1 = new SearchEngine();
        Set<String> searchSet2 = new HashSet<>();
        searchSet2.add("c");
        searchSet2.add("b");
        searchEngine1.setUserId(2);
        searchEngine1.setSearchTermSet(searchSet2);
        searchRepo.save(searchEngine1);

        List<SearchEngine> searchEngineList = searchRepo.findAll();
        assertTrue(searchEngineList.get(1).getSearchTermSet().contains("c"));
    }
}