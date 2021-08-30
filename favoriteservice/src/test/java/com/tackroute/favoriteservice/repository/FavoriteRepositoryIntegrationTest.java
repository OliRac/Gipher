package com.tackroute.favoriteservice.repository;


import com.tackroute.favoriteservice.domain.Selection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
    private HashSet<String> favoriteList2 = new HashSet<String>() {{
        add(gif1);
    }};
    private Selection selection;

    @Test
    public void givenGifToSaveAsFavoriteThenShouldReturnSavedGif() {
        String gif3 = "https://giphy.com/gifs/mlb-y0FfnDHoT6BKfKzzMZ";

        selection = new Selection(1, favoriteList1);
        Selection selection1 = favoriteRepository.save(selection);
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
        assertEquals(favoriteRepository.findByUserId(selection.getUserId()), null);

    }

    @Test
    public void givenUserIdThenShouldReturnListOfAllFavorites(){
        selection = new Selection(1, favoriteList1);
        Selection sel= favoriteRepository.save(selection);
        HashSet<String> favorites = sel.getFavoriteList();
        assertEquals(favorites, favoriteList1 );
    }

}
