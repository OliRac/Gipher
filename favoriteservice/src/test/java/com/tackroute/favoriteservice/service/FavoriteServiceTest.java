package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.repository.FavoriteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FavoriteServiceTest {
    String gif1 = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
    String gif2 = "https://giphy.com/stories/welcome-to-the-high-rollers-room-8d7b09cb-a920";



    private HashSet<String> favoriteList = new HashSet<String>() {{
        add(gif1);
        add(gif2);

    }};

    private HashSet<String> favoriteList2 = new HashSet<String>() {{
        add(gif1);
    }};

    @Mock
    private FavoriteRepository favoriteRepository;

    @InjectMocks
    private FavoriteServiceImpl favoriteService;

    private Selection selection;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        selection = new Selection(1, favoriteList );

    }

    @AfterEach
    public void tearDown() {
        selection = null;
    }

    @Test
    void givenFirstFavoriteGifToAddThenShouldReturnCreatedSelection() throws GifAlreadyExistException {
        when(favoriteRepository.save(selection)).thenReturn(selection);
        assertEquals(selection, favoriteRepository.save(selection));
        verify(favoriteRepository, times(1)).save(selection);
    }

    @Test
    void givenLastFavoriteGifToRemoveThenShouldReturnDeletedSelection() throws GifNotFoundException, NoFavoriteGifFoundException {
        Selection s2 = favoriteRepository.save(new Selection(1, favoriteList2));
        favoriteRepository.deleteByUserId(1);
        assertEquals(favoriteRepository.findByUserId(1), null);
        verify(favoriteRepository, times(1)).deleteByUserId(1);
    }

    }
