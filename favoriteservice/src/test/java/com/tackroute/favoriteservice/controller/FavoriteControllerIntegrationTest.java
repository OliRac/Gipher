package com.tackroute.favoriteservice.controller;

import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.service.FavoriteService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@DataMongoTest
//@ExtendWith(SpringExtension.class)
public class FavoriteControllerIntegrationTest {

    @Autowired
    private FavoriteService favoriteService;

    private Selection selection;

    String gif1 = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
    String gif2 = "https://giphy.com/stories/welcome-to-the-high-rollers-room-8d7b09cb-a920";


    private HashSet<String> favoriteList;

    @BeforeEach
    void setUp() {
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
        Selection updatedSelection = favoriteService.addFavorite(1, gif1);
        assertNotNull(updatedSelection);
        assertEquals(updatedSelection.getFavoriteList(), selection.getFavoriteList());
        assertEquals(updatedSelection.getUserId(), selection.getUserId());

    }

    @Test
    void givenFavoriteGifToAddThenThrowException() throws GifAlreadyExistException {
        assertNotNull(favoriteService.addFavorite(1, gif1));
        assertThrows(GifAlreadyExistException.class, () -> favoriteService.addFavorite(1, gif1));
    }

    @Test
    void givenFavoriteGifToRemoveThenShouldReturnTheRemovedGif() throws GifNotFoundException, NoFavoriteGifFoundException {
        assertNotNull(favoriteService.addFavorite(1, gif1));
        HashSet<String> updatedSelection = favoriteService.removeFavorite(1, gif1);
        assertNotNull(updatedSelection);
    }

    @Test
    void givenFavoriteGifToRemoveThenThrowNoGifFoundException() throws RuntimeException {
        assertThrows(RuntimeException.class,  () -> favoriteService.removeFavorite(1, gif1));
//        assertThrows()
    }
//    @Test
//    void givenFavoriteGifToRemoveThenThrowNoGifExistException() throws NoFavoriteGifFoundException {
//        assertThrows(NoFavoriteGifFoundException.class,  () -> favoriteService.removeFavorite(1, gif1));
//
//    }

    @Test
    void givenCallToGetAllFavoriteGifsThenListShouldReturnAllFavorites() throws NoFavoriteGifFoundException {
        HashSet<String> listFavs = new HashSet<String>(){ {add(gif1);}};
        assertNotNull(favoriteService.addFavorite(1, gif1));
        HashSet<String> retrievedGifs = favoriteService.getAllFavorites(1);
        assertNotNull(retrievedGifs);
        assertEquals(listFavs, retrievedGifs);
    }


    @Test
    void givenCallToGetAllFavoritesThenThrowException() throws NoFavoriteGifFoundException {
        assertThrows(NoFavoriteGifFoundException.class, () -> favoriteService.getAllFavorites(1));
    }

    @Test
    void givenUserIdThenShouldReturnEmptyFavoriteList() throws NoFavoriteGifFoundException {
       Selection selection1 = favoriteService.addFavorite(1, gif1);
       HashSet<String> emptySet=  new HashSet<String>();
        HashSet<String> emptyList= favoriteService.emptyFavoriteList(selection1.getUserId());

        assertEquals(emptySet,  emptyList);
    }

    @Test
    void givenUserIdToEmptyFavoritesThenThrowException() throws NoFavoriteGifFoundException {
        assertThrows(NoFavoriteGifFoundException.class, () -> favoriteService.emptyFavoriteList(1));
    }

//    @Test
//    void givenBlogIdThenShouldThrowException() throws BlogNotFoundException {
//        assertThrows(BlogNotFoundException.class, () -> blogService.getBlogById(blog.getBlogId()));
//    }


}
