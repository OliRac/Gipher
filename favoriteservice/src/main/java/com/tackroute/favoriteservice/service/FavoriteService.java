package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.model.Selection;

import java.util.HashMap;
import java.util.HashSet;

public interface FavoriteService {

    /**
     * Abstract Method to add a particular gif to the favorite list of the user
     */
    Selection addFavorite(int userId, String gifUrl) throws GifAlreadyExistException;

    /**
     * Abstract Method to remove a particular gif of the favorite list
     */
    HashSet<String> removeFavorite( int userId, String gifUrl) throws GifNotFoundException;

    /**
     * Abstract Method to delete remove all the favorites from the list
     */
    HashSet<String> emptyFavoriteList(int userId);

    /**
     * Abstract Method to delete get all the gifs in the favorite list
     */
    HashSet<String> getAllFavorites(int userId) throws NoFavoriteGifFoundException;

    /**
     * Abstract Method to check if a gif belongs to the favorites list
     */
    boolean checkIfFavoriteByUrl( int userId, String gifUrl);






}
