package com.stackroute.recommendedservice.controller;

import com.stackroute.recommendedservice.exception.GlobalExceptionHandler;
import com.stackroute.recommendedservice.exception.UserNotFoundException;
import com.stackroute.recommendedservice.service.RecommendationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
class RecommendationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RecommendationServiceImpl recommendationService;

    @InjectMocks
    private RecommendationController recommendationController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void addTermShouldReturnAddedTerm() throws Exception{
        int userId = 0;
        String term = "term";

        when(recommendationService.addTerm(anyInt(), anyString())).thenReturn(term);
        mockMvc.perform(post("/api/v1/addTerm/" + userId + "/" + term)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String response = mvcResult.getResponse().getContentAsString();
                    assertEquals(term, response);
                });

        verify(recommendationService, times(1)).addTerm(anyInt(), anyString());
    }

    @Test
    public void getRecommendationShouldReturnRecommendationForExistingUser() throws Exception{
        int userId = 0;
        String recommendations = "things";

        when(recommendationService.getRecommendation(anyInt())).thenReturn(recommendations);
        mockMvc.perform(get("/api/v1/recommendation/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String response = mvcResult.getResponse().getContentAsString();
                    assertEquals(recommendations, response);
                });

        verify(recommendationService, times(1)).getRecommendation(anyInt());
    }

    @Test
    public void getRecommendationShouldReturnThrowUserNotFoundException() throws Exception {
        int userId = 0;

        when(recommendationService.getRecommendation(anyInt())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(get("/api/v1/recommendation/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isNotFound());

        verify(recommendationService, times(1)).getRecommendation(anyInt());
    }

    @Test
    public void getTrendingShouldReturnTrending() throws Exception {
        String trending = "trending";

        when(recommendationService.getTrending()).thenReturn(trending);
        mockMvc.perform(get("/api/v1/trending")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isOk())
                .andDo(mvcResult -> {
                    String response = mvcResult.getResponse().getContentAsString();
                    assertEquals(trending, response);
                });

        verify(recommendationService, times(1)).getTrending();
    }
}