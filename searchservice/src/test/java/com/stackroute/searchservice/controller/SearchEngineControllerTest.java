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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SearchEngineControllerTest {
    private MockMvc mockMvc;
    @Mock
    SearchEngineService searchService;
    @InjectMocks
    private SearchEngineController searchController;

    private SearchEngine search;
    private Set<String> searchSet;
    private List<SearchEngine> searchEngineList;
    private final String URL = "/api/v1/search-service/";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(searchController).build();
        search = new SearchEngine();
        searchSet = new HashSet<>();
        searchEngineList = new ArrayList<>();
        search.setUserId(1);
        searchSet.add("book");

        searchEngineList.add(search);

    }
    @AfterEach
    public void tearDown() {
        search = null;
    }

    @Test
    public void givenSearchEngineToSaveThenShouldReturnSavedSearch() throws Exception {
        when(searchService.saveSearch(any())).thenReturn(search);
        mockMvc.perform(post(URL + "search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(search)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(searchService).saveSearch(any());
    }

    @Test
    public void givenGetAllSearchesThenShouldReturnListOfAllSearchEngine() throws Exception {
        when(searchService.getAllSearch()).thenReturn(searchEngineList);
        mockMvc.perform(MockMvcRequestBuilders.get(URL + "searches")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(search)))
                .andDo(MockMvcResultHandlers.print());
        verify(searchService).getAllSearch();
        verify(searchService, times(1)).getAllSearch();

    }

    @Test
    void givenSearchUserIdThenShouldReturnRespectiveSearch() throws Exception {
        when(searchService.findByUserId(search.getUserId())).thenReturn(search);
        mockMvc.perform(get(URL + "search/1"))
                .andExpect(MockMvcResultMatchers.status()
                        .isFound())
                .andDo(MockMvcResultHandlers.print());

    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
