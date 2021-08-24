package com.stackroute.searchservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.service.SearchEngineService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class SearchEngineControllerTest {
    private MockMvc mockMvc;
    @Mock
    SearchEngineService searchService;
    @InjectMocks
    private SearchEngineController searchController;

    private SearchEngine search;
    private Set<String> searchTerms = new HashSet();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();

        search = new SearchEngine(1 , searchTerms);
        searchTerms.add("book");
        searchTerms.add("happy");
    }
    @AfterEach
    public void tearDown() {
        search = null;
    }
    public static String parsToString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test   //1
    public void givenSearchTermThenShouldReturnLimitedNumberOfGif() throws Exception {
      //  when(searchService.getAllSearch(search.getId())).thenReturn(search);
        when(searchService.saveSearch(search.getUserId(), "book")).thenReturn(search);
        mockMvc.perform(get("/search?searchTerm={searchTerm}&userId={userId}"))
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}