package com.tackroute.favoriteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tackroute.favoriteservice.domain.UserGifDto;
import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.exception.GlobalExceptionHandler;
import com.tackroute.favoriteservice.service.FavoriteService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FavoriteService favoriteService;

    Selection selection;
    UserGifDto dto1, dto2;

    String gif1 = "1111";
    String gif2 = "gif2";


    private HashSet<String> favoriteList;

    @InjectMocks
    private FavoriteController favoriteController;


    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favoriteController).setControllerAdvice(new GlobalExceptionHandler()).build();
        selection = new Selection(1, favoriteList );
        dto1 = new UserGifDto(selection.getUserId(),  gif1);
        dto2 = new UserGifDto(selection.getUserId(),  gif2);
        favoriteList = new HashSet<>();
        favoriteList.add(dto1.getGifUrl());
    }

    @AfterEach
    public void tearDown() {
        selection = null;
        dto1 = null;
        dto2 = null;
    }

    @Test
    public void givenGifToSaveAsFavoriteThenShouldReturnSavedGif() throws Exception {
        String gif="111";
        HashSet<String> favoriteList2 = new HashSet<String>(){  { add(gif);} };
        Selection selection1 = new Selection(selection.getUserId(), favoriteList2);

        when(favoriteService.addFavorite(any())).thenReturn(selection1);
        mockMvc.perform(post("/api/v1/favorite-service/addFavorite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto1)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenGifUrlToAddThenShouldNotReturnAddedFavorite() throws Exception {
        when(favoriteService.addFavorite(any())).thenThrow(GifAlreadyExistException.class);
        mockMvc.perform(post("/api/v1/favorite-service/addFavorite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto1)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenGifUrlToRemoveThenShouldReturnRemovedGif() throws Exception {

       when(favoriteService.removeFavorite(any())).thenReturn(favoriteList);
       mockMvc.perform(put("/api/v1/favorite-service/removeFavorite")
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(dto1)))
               .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
   }

    @Test
    void givenGifUrlToRemoveThenShouldNotReturnRemovedBlog() throws  Exception {
        when(favoriteService.removeFavorite(any())).thenThrow(GifNotFoundException.class);
        mockMvc.perform(put("/api/v1/favorite-service/removeFavorite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(dto1)))
                .andExpect(MockMvcResultMatchers.status().isNotFound()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenUserIdThenShouldReturnEmptyFavoriteList() throws Exception {
        HashSet<String> favoriteList3 = new HashSet<String>();
        when(favoriteService.emptyFavoriteList(selection.getUserId())).thenReturn(favoriteList3);
        mockMvc.perform(delete("/api/v1/favorite-service/emptyFavoriteList/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(favoriteList3)))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void givenUserIdThenShouldReturnListOfAllFavorites() throws Exception {
        when(favoriteService.getAllFavorites(selection.getUserId())).thenReturn(asJsonString(favoriteList));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/favorite-service/favorites/1")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(favoriteList)))
                .andDo(MockMvcResultHandlers.print());
        verify(favoriteService).getAllFavorites(selection.getUserId());
        verify(favoriteService, times(1)).getAllFavorites(selection.getUserId());


    }

    public static String asJsonString(final Object obj) {
        try {
            System.out.println(new ObjectMapper().writeValueAsString(obj));
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
