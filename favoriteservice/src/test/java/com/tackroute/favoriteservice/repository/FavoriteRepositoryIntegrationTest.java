package com.tackroute.favoriteservice.repository;


import com.tackroute.favoriteservice.domain.Selection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FavoriteRepositoryIntegrationTest {

    @Autowired
    private FavoriteRepository favoriteRepository;

    private String gif1 = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
    String gif2 = "https://giphy.com/stories/welcome-to-the-high-rollers-room-8d7b09cb-a920";

    private HashSet<String> favoriteList1 = new HashSet<String>() {{
        add(gif1);
        add(gif2);

    }};
    private Selection selection;

    @Test
    public void givenGifToSaveAsFavoriteThenShouldReturnSavedGif() {
        String gif3 = "https://giphy.com/gifs/mlb-y0FfnDHoT6BKfKzzMZ";

        selection = new Selection(1, favoriteList1);
        favoriteRepository.save(selection);
        Selection selection1 = favoriteRepository.findByUserId(selection.getUserId());
        selection1.setUserId(selection.getUserId());
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
        selection1.setUserId(selection.getUserId());
        selection1.removeFavoriteItem(gif2);
        Selection updatedSelection = favoriteRepository.save(selection1);
        assertEquals(selection1.getUserId(), updatedSelection.getUserId());
        assertEquals(selection1.getFavoriteList(), updatedSelection.getFavoriteList());
    }

    @Test
    public void givenUserIdThenShouldReturnEmptyFavoriteList(){
        Selection selection2 = new Selection(1, new HashSet<String>());

        Selection selection1 = favoriteRepository.findByUserId(selection.getUserId());
        selection1.emptyFavoriteList();
        Selection emptySelection = favoriteRepository.save(selection1);
        assertEquals(emptySelection, selection2);

    }

    @Test
    public void givenUserIdThenShouldReturnListOfAllFavorites(){
        selection = new Selection(1, favoriteList1);

        Selection selection2 = new Selection(1, new HashSet<String>() {
            {add("https://giphy.com/gifs/mlb-y0FfnDHoT6BKfKzzMZ");}
        });

        favoriteRepository.save(selection2);
        favoriteRepository.save(selection);

        HashSet<String> favList = new HashSet<String >() {{
            add(gif1);
            add(gif2);
        }};

        HashSet<String> favorites = favoriteRepository.findByUserId(selection.getUserId()).getFavoriteList();
        assertEquals(favList, favorites );
    }

//    @Test
//    public void givenGifUrlReturnIfGifIsFavoriteOrNot(){
//        String gif = "https://giphy.com/stories/some-bb23-cute-moments-1b9c561f-c095";
//    }
}
