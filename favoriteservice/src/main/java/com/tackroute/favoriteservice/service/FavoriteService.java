package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.domain.UserGifDto;
import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;

import java.util.HashSet;

public interface FavoriteService {

    /**
     * Abstract Method to add a particular gif to the favorite list of the user
     */
    Selection addFavorite(UserGifDto userGifDto) throws GifAlreadyExistException;

    /**
     * Abstract Method to remove a particular gif of the favorite list
     */
    HashSet<String> removeFavorite( UserGifDto userGifDto) throws GifNotFoundException, NoFavoriteGifFoundException;

    /**
     * Abstract Method to delete remove all the favorites from the list
     */
    HashSet<String> emptyFavoriteList(int userId) throws NoFavoriteGifFoundException;

    /**
     * Abstract Method to delete get all the gifs in the favorite list
     */
    HashSet<String> getAllFavorites(int userId) throws NoFavoriteGifFoundException;

    /**
     * Abstract Method to check if a gif belongs to the favorites list
     */
    boolean checkIfFavoriteByUrl( UserGifDto userGifDto) throws  NoFavoriteGifFoundException;






}
