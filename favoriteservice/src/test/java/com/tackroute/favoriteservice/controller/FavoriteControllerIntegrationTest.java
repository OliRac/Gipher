package com.tackroute.favoriteservice.controller;

import com.tackroute.favoriteservice.domain.UserGifDto;
import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.service.FavoriteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FavoriteControllerIntegrationTest {

    @Autowired
    private FavoriteService favoriteService;

    private Selection selection;
    private UserGifDto dto1, dto2, dto3;

    String gif1 = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
    String gif2 = "https://giphy.com/stories/welcome-to-the-high-rollers-room-8d7b09cb-a920";


    private HashSet<String> favoriteList;

    @BeforeEach
    void setUp() {

        dto1 = new UserGifDto(1, gif1);
        dto2 = new UserGifDto(1, gif2);
        dto3 = new UserGifDto(2, gif1);
        favoriteList = new HashSet<String>() {{ add(gif1);}};
        selection = new Selection(1, favoriteList );
    }

    @AfterEach
    public void tearDown() {
        favoriteList=null;
        selection = null;
    }

    @Test
    void givenFavoriteGifToAddThenShouldReturnAddedGif() throws GifAlreadyExistException {
        Selection updatedSelection = favoriteService.addFavorite(dto1);
        assertNotNull(updatedSelection);
        assertEquals(updatedSelection.getFavoriteList(), selection.getFavoriteList());
        assertEquals(updatedSelection.getUserId(), selection.getUserId());

    }

    @Test
    void givenFavoriteGifToAddThenThrowException() throws GifAlreadyExistException {
        assertNotNull(favoriteService.addFavorite(dto1));
        assertThrows(GifAlreadyExistException.class, () -> favoriteService.addFavorite(dto1));
    }

    @Test
    void givenFavoriteGifToRemoveThenShouldReturnTheRemovedGif() throws GifNotFoundException, NoFavoriteGifFoundException {
        assertNotNull(favoriteService.addFavorite(dto1));
        HashSet<String> updatedSelection = favoriteService.removeFavorite(dto1);
        assertNotNull(updatedSelection);
    }

    @Test
    void givenFavoriteGifToRemoveThenThrowNoGifFoundException() throws GifNotFoundException {
        assertThrows(GifNotFoundException.class,  () -> favoriteService.removeFavorite(dto2));
    }
    @Test
    void givenFavoriteGifToRemoveThenThrowNoGifExistException() throws NoFavoriteGifFoundException {
        assertThrows(NoFavoriteGifFoundException.class,  () -> favoriteService.removeFavorite(dto3));

    }

    @Test
    void givenCallToGetAllFavoriteGifsThenListShouldReturnAllFavorites() throws NoFavoriteGifFoundException {
        HashSet<String> listFavs = new HashSet<String>(){ { add(gif2);}};
        assertNotNull(favoriteService.addFavorite(dto2));
        HashSet<String> retrievedGifs = favoriteService.getAllFavorites(1);
        assertNotNull(retrievedGifs);
        assertEquals(listFavs, retrievedGifs);
    }


    @Test
    void givenCallToGetAllFavoritesThenThrowException() throws NoFavoriteGifFoundException {
        assertThrows(NoFavoriteGifFoundException.class, () -> favoriteService.getAllFavorites(2));
    }

    @Test
    void givenUserIdThenShouldReturnEmptyFavoriteList() throws NoFavoriteGifFoundException {
       HashSet<String> emptySet=  new HashSet<String>();
        HashSet<String> emptyList= favoriteService.emptyFavoriteList(1);

        assertEquals(emptySet,  emptyList);
    }

    @Test
    void givenUserIdToEmptyFavoritesThenThrowException() throws NoFavoriteGifFoundException {
        assertThrows(NoFavoriteGifFoundException.class, () -> favoriteService.emptyFavoriteList(2));
    }

}
