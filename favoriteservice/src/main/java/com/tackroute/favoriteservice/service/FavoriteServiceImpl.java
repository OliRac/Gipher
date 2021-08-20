package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.model.Selection;
import com.tackroute.favoriteservice.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class FavoriteServiceImpl implements FavoriteService{

   private FavoriteRepository favoriteRepository;

   @Autowired
   public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {this.favoriteRepository = favoriteRepository;}

    @Override
    public Selection addFavorite(int userId, String gifUrl) throws GifAlreadyExistException {
      Selection savedFavorite;
       if(!favoriteRepository.existsById(userId)){

       }

        return null;
    }

    @Override
    public HashSet<String> removeFavorite(int userId, String gifUrl) throws GifNotFoundException {
        return null;
    }

    @Override
    public HashSet<String> emptyFavoriteList(int userId) {
        return null;
    }

    @Override
    public HashSet<String> getAllFavorites(int userId)  throws NoFavoriteGifFoundException {
        return null;
    }

    @Override
    public boolean checkIfFavoriteByUrl(int userId, String gifUrl) {
        return false;
    }
}
