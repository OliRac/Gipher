package com.tackroute.favoriteservice.repository;


import com.tackroute.favoriteservice.controller.FavoriteController;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.exception.GlobalExceptionHandler;
import com.tackroute.favoriteservice.service.FavoriteService;
import com.tackroute.favoriteservice.service.FavoriteServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@ExtendWith(SpringExtension.class)
@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class FavoriteRepositoryIntegrationTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    private MockMvc mockMvc;

    @Mock
    private FavoriteService favoriteService2;

    @InjectMocks
    private FavoriteController favoriteController;

    private String gif1 = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
    String gif2 = "https://giphy.com/stories/welcome-to-the-high-rollers-room-8d7b09cb-a920";

    private HashSet<String> favoriteList1 = new HashSet<String>() {{
        add(gif1);
        add(gif2);

    }};
    private HashSet<String> favoriteList2 = new HashSet<String>() {{
        add(gif1);
    }};
    private Selection selection;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(favoriteController).setControllerAdvice(new GlobalExceptionHandler()).build();

    }

    @Test
    public void givenGifToSaveAsFavoriteThenShouldReturnSavedGif() {
        String gif3 = "https://giphy.com/gifs/mlb-y0FfnDHoT6BKfKzzMZ";

        selection = new Selection(1, favoriteList1);
        favoriteRepository.save(selection);
        Selection selection1 = favoriteRepository.findByUserId(selection.getUserId());
        selection1.getFavoriteList().add(gif3);
        selection1.addFavoriteItem(gif3);
        Selection updatedSelection = favoriteRepository.save(selection1);
        assertEquals(selection1.getUserId(), updatedSelection.getUserId());
        assertEquals(selection1.getFavoriteList(), updatedSelection.getFavoriteList());

    }
    @Test
    public void givenGifUrlToRemoveThenShouldReturnRemovedGif() {
        selection = new Selection(1, favoriteList1);
        favoriteRepository.save(selection);
        Selection selection1 = favoriteRepository.findByUserId(selection.getUserId());
        selection1.getFavoriteList().remove(gif2);
        Selection updatedSelection = favoriteRepository.save(selection1);
        assertEquals(selection1.getUserId(), updatedSelection.getUserId());
        assertEquals(updatedSelection.getFavoriteList(), favoriteList2);
    }

    @Test
    public void givenLastFavoriteToRemoveThenShouldDeleteFavoriteList() {
        Selection emptySelection = new Selection(1, new HashSet<String>());
        selection = new Selection(1, favoriteList2);
        Selection selection1 = favoriteRepository.save(selection);
        selection1.getFavoriteList().remove(gif1);
        favoriteRepository.deleteByUserId(1);
        assertEquals(favoriteRepository.findByUserId(1), null);
        assertEquals(selection1.getFavoriteList(),emptySelection.getFavoriteList());

    }

        @Test
        public void givenUserIdThenShouldEmptyFavoriteList(){
        selection = new Selection(1, favoriteList1);
        favoriteRepository.save(selection);
        favoriteRepository.deleteByUserId(selection.getUserId());
//        Selection selection1 = favoriteRepository.findByUserId(selection.getUserId());
//        selection1.emptyFavoriteList();
//        Selection emptySelection = favoriteRepository.save(selection1);
        assertEquals(favoriteRepository.findByUserId(selection.getUserId()), null);

    }

    @Test
    public void givenUserIdThenShouldReturnListOfAllFavorites(){
        selection = new Selection(1, favoriteList1);
        Selection sel= favoriteRepository.save(selection);
//        favoriteRepository.findByUserId(selection.getUserId())
        HashSet<String> favorites = sel.getFavoriteList();
        assertEquals(favorites, favoriteList1 );
    }

//    @Test
//    public void givenGifUrlReturnIfGifIsFavoriteOrNot(){
//        String gif = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
//    }
}
