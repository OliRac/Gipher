package com.stackroute.recommendedservice.service;

import com.stackroute.recommendedservice.repository.UserTermsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserTermsServiceImplTest {

    @Mock
    private UserTermsRepository userTermsRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private RecommendationServiceImpl recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void getTrendingShouldReturnOK() {
        /*when(recommendationService.queryTenor(any(), any(), any())).thenReturn("OK");
        String result = recommendationService.getTrending();
        assertNotNull(result);*/
    }
}