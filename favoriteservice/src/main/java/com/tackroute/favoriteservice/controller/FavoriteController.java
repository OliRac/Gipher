package com.tackroute.favoriteservice.controller;

import com.tackroute.favoriteservice.domain.UserGifDto;
import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import lombok.extern.java.Log;
// import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@RestController
@RequestMapping(value = "/api/v1/favorite-service")
public class FavoriteController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private FavoriteService favoriteService;


    @Autowired
    public FavoriteController(FavoriteService favoriteService){
        this.favoriteService =favoriteService;
    }

    @GetMapping(value = "/favorites/{userId}")
    public ResponseEntity<?> getAllFavorites(@PathVariable int userId) throws NoFavoriteGifFoundException{
        LOG.info("Sending get request to get all the favorite gifs of the user");
        return new ResponseEntity<>( favoriteService.getAllFavorites(userId), HttpStatus.OK);
    }


    @PostMapping(value = "/addFavorite")
    public ResponseEntity<Selection> addFavorite(@RequestBody UserGifDto userGifDto) throws GifAlreadyExistException {
        LOG.info("Sending post request to add a new gif to the favorites of the user");

        return new ResponseEntity<Selection>(favoriteService.addFavorite(userGifDto), HttpStatus.OK);
    }

    @PutMapping(value = "/removeFavorite")
    public ResponseEntity<HashSet<String>> removeFavorite(@RequestBody UserGifDto userGifDto)  throws GifNotFoundException, NoFavoriteGifFoundException{
        LOG.info("Sending post request to remove a gif to the favorites of the user");

        return new ResponseEntity<HashSet<String>>(favoriteService.removeFavorite(userGifDto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/emptyFavoriteList/{userId}")
    public ResponseEntity<HashSet<String>> emptyFavoriteList(@PathVariable int userId) throws NoFavoriteGifFoundException {
        LOG.info("Sending delete request to delete all the favorites of the user");
        return new ResponseEntity<HashSet<String>>(favoriteService.emptyFavoriteList(userId), HttpStatus.OK);
    }

    @PostMapping("/favorite")
    public ResponseEntity<Boolean> checkIfFavoriteByUrl(@RequestBody UserGifDto userGifDto) throws NoFavoriteGifFoundException {
        return new ResponseEntity<Boolean>(favoriteService.checkIfFavoriteByUrl(userGifDto), HttpStatus.OK);
    }









}
