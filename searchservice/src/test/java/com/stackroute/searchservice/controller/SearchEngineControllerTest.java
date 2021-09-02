package com.stackroute.searchservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.UserTermDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void givenUserTermDTOGetGifShouldCallSaveSearch() throws Exception {
        UserTermDTO userTerm = new UserTermDTO(1, "book");
        when(searchService.saveSearch(any(UserTermDTO.class))).thenReturn(search);
        mockMvc.perform(post(URL + "gifs/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(userTerm)))
                .andExpect(status().isOk());

        verify(searchService, times(1)).saveSearch(any(UserTermDTO.class));
    }

    @Test
    public void givenGetAllSearchesThenShouldReturnListOfAllSearchEngine() throws Exception {
        when(searchService.getAllSearch()).thenReturn(searchEngineList);
        mockMvc.perform(get(URL + "searches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk());

        verify(searchService, times(1)).getAllSearch();
    }

    @Test
    void givenSearchUserIdThenShouldReturnRespectiveSearch() throws Exception {
        when(searchService.findByUserId(search.getUserId())).thenReturn(search);
        mockMvc.perform(get(URL + "search/" + search.getUserId()))
                .andExpect(status().isFound())
                .andDo(MockMvcResultHandlers.print());
        verify(searchService, times(1)).findByUserId(anyInt());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
