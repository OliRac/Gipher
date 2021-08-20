package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.repository.FavoriteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

//    @Test
//    void givenFavoriteGifToAddThenShouldReturnAddedGif() throws GifAlreadyExistException {
//        favoriteRepository.findById(1).
//        when(favoriteRepository.(any())).thenReturn(selection);
//        assertEquals(blog, blogService.saveBlog(blog));
//        verify(blogRepository, times(1)).save(any());
//
//
//        Selection updatedSelection = favoriteService.addFavorite(1, gif1);
//        assertNotNull(updatedSelection);
//        assertEquals(updatedSelection.getFavoriteList(), selection.getFavoriteList());
//        assertEquals(updatedSelection.getUserId(), selection.getUserId());
//
//    }
//
//    @Test
//    void givenFavoriteGifToRemoveThenShouldReturnTheRemovedGif() throws GifNotFoundException {
//        assertNotNull(favoriteService.addFavorite(1, gif1));
//        HashSet<String> updatedSelection = favoriteService.removeFavorite(1, gif1);
//        assertNotNull(updatedSelection);
//    }
//
//    @Test
//    void givenCallToGetAllFavoriteGifsThenListShouldReturnAllFavorites() throws NoFavoriteGifFoundException {
//        HashSet<String> listFavs = new HashSet<String>(){ {add(gif1);}};
//        assertNotNull(favoriteService.addFavorite(1, gif1));
//        HashSet<String> retrievedGifs = favoriteService.getAllFavorites(1);
//        assertNotNull(retrievedGifs);
//        assertEquals(listFavs, retrievedGifs);
//    }
//
//
//    @Test
//    void givenUserIdThenShouldReturnEmptyFavoriteList() throws NoFavoriteGifFoundException {
//        Selection selection1 = favoriteService.addFavorite(1, gif1);
//        Selection selection2= new Selection(selection1.getUserId(), new HashSet<String>());
//        HashSet<String> emptyList= favoriteService.emptyFavoriteList(selection1.getUserId());
//
//        assertEquals(selection2, new Selection(1, emptyList));
//    }



}
