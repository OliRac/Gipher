package com.tackroute.favoriteservice.controller;

import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1")
public class FavoriteController {



    private FavoriteService favoriteService;


    @Autowired
    public FavoriteController(FavoriteService favoriteService){
        this.favoriteService =favoriteService;
    }

    @GetMapping(value = "/favorites/{userId}")
    public ResponseEntity<HashSet<String>> getAllFavorites(@PathVariable int userId) throws NoFavoriteGifFoundException{
        return new ResponseEntity<HashSet<String>>( favoriteService.getAllFavorites(userId), HttpStatus.OK);

    }



    @PostMapping(value = "/addFavorite/{userId}/{gifUrl}")
    public ResponseEntity<Selection> addFavorite(@PathVariable int userId, @PathVariable String gifUrl) throws GifAlreadyExistException {
        return new ResponseEntity<Selection>(favoriteService.addFavorite(userId,gifUrl), HttpStatus.OK);

    }

    @PostMapping(value = "/removeFavorite/{userId}/{gifUrl}")
    public ResponseEntity<HashSet<String>> removeFavorite(@PathVariable int userId, @PathVariable String gifUrl)  throws GifNotFoundException, NoFavoriteGifFoundException{
        return new ResponseEntity<HashSet<String>>(favoriteService.removeFavorite(userId,gifUrl), HttpStatus.OK);

    }

    @DeleteMapping(value = "/emptyFavoriteList/{userId}")
    public ResponseEntity<HashSet<String>> emptyFavoriteList(@PathVariable int userId) throws NoFavoriteGifFoundException {
        return new ResponseEntity<HashSet<String>>(favoriteService.emptyFavoriteList(userId), HttpStatus.OK);
    }

    @GetMapping("/favorite/{userId}/{gifUrl}")
    public ResponseEntity<Boolean> checkIfFavoriteByUrl(@PathVariable(value="userId") int userId, @PathVariable String gifUrl) throws NoFavoriteGifFoundException {
        return new ResponseEntity<Boolean>(favoriteService.checkIfFavoriteByUrl(userId, gifUrl), HttpStatus.OK);
    }









}
