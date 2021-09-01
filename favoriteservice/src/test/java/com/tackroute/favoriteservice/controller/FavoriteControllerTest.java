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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FavoriteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FavoriteService favoriteService;
    Selection selection;
    UserGifDto dto1, dto2;


    String gif1 = "gif1";
    String gif2 = "gif2";


    private HashSet<String> favoriteList = new HashSet<String>() {{
        add(gif1);

    }};

    @InjectMocks
    private FavoriteController favoriteController;


    @BeforeEach
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        dto1 = new UserGifDto(selection.getUserId(),  gif2);
        mockMvc = MockMvcBuilders.standaloneSetup(favoriteController).setControllerAdvice(new GlobalExceptionHandler()).build();
        selection = new Selection(1, favoriteList );
        dto1 = new UserGifDto(selection.getUserId(),  "111");

    }

    @AfterEach
    public void tearDown() {
        selection = null;
    }

    @Test
    public void givenGifToSaveAsFavoriteThenShouldReturnSavedGif() throws Exception {
//
        String gif="111";
        HashSet<String> favoriteList2 = new HashSet<String>(){  { add(gif);} };
        Selection selection1 = new Selection(selection.getUserId(), favoriteList2);

        when(favoriteService.addFavorite(dto1)).thenReturn(selection1);
        mockMvc.perform(post("/api/v1/favorite-service/addFavorite/1/111")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(selection1)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void givenGifUrlToAddThenShouldNotReturnAddedFavorite() throws Exception {
        when(favoriteService.addFavorite(dto1)).thenThrow(GifAlreadyExistException.class);
        mockMvc.perform(post("/api/v1/favorite-service/addFavorite/1/111")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString("Gif already exists among the favorites!")))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void givenGifUrlToRemoveThenShouldReturnRemovedGif() throws Exception {
       HashSet<String> favoriteList1 = new HashSet<String>(){
                                                { add(gif1);}
                                             };
       when(favoriteService.removeFavorite(dto2)).thenReturn(favoriteList1);
       mockMvc.perform(put("/api/v1/favorite-service/removeFavorite/1/gif2")
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(favoriteList1)))
               .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());

   }

    @Test
    void givenGifUrlToRemoveThenShouldNotReturnRemovedBlog() throws  Exception {
        when(favoriteService.removeFavorite(dto2)).thenThrow(GifNotFoundException.class);
        mockMvc.perform(put("/api/v1/favorite-service/removeFavorite/1/gif2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(favoriteList)))
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
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
