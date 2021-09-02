package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.model.UserTermDTO;
import com.stackroute.searchservice.repository.SearchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchEngineServiceTest {
    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private SearchEngineServiceImpl searchService;
    private SearchEngine search , search1;
    private Set<String> searchSet;
    private List<SearchEngine> searchEngineList;
    private UserTermDTO dto;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        dto = new UserTermDTO();
        search = new SearchEngine();
        dto.setUserId(-1);
        dto.setSearchTerm("a");
        dto.setSearchTerm("b");
    }

    @AfterEach
    public void tearDown() {
        search = null;
    }

    @Test
    public void givenSearchEngineDTOToSaveThenShouldReturnSavedSearch() {
        when(searchRepository.save(any())).thenReturn(search);
        assertEquals(search, searchService.saveSearch(dto));
        verify(searchRepository, times(1)).save(any());
    }

    @Test
    public void givenGetAllSearchThenShouldReturnListOfAllSearch() {
        searchRepository.save(search);
        when(searchRepository.findAll()).thenReturn(searchEngineList);
        List<SearchEngine> searchEngineList1 = searchService.getAllSearch();
        assertEquals(searchEngineList, searchEngineList1);
        verify(searchRepository, times(1)).save(search);
        verify(searchRepository, times(1)).findAll();
    }

    @Test
    public void givenUserIdThenShouldReturnRespectiveSearchEngine() {
        when(searchRepository.findByUserId(anyInt())).thenReturn((search));
        SearchEngine fetchSearch = searchService.findByUserId(search.getUserId());
        verify(searchRepository, times(1)).findByUserId(anyInt());

    }
}