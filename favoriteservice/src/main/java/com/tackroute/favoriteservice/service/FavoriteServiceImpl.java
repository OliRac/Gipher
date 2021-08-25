package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.repository.FavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class FavoriteServiceImpl implements FavoriteService{

    private final Logger LOG = LoggerFactory.getLogger(getClass());


    private FavoriteRepository favoriteRepository;

   @Autowired
   public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {this.favoriteRepository = favoriteRepository;}

    @Override
    public Selection addFavorite(int userId, String gifUrl) throws GifAlreadyExistException {
//        System.out.println(favoriteRepository.existsByUserId(userId));

        Selection userSelections = favoriteRepository.findByUserId(userId);

        if(userSelections !=null) {
            if(userSelections.getFavoriteList().contains(gifUrl)){
                throw new GifAlreadyExistException();
            }
            else {
                userSelections.getFavoriteList().add(gifUrl);
            }
        } else {

                var set = new HashSet<String>();
                set.add(gifUrl);
                userSelections = new Selection(userId, set);

        }

        return favoriteRepository.save(userSelections);

    }


    @Override
    public HashSet<String> removeFavorite(int userId, String gifUrl) throws GifNotFoundException, NoFavoriteGifFoundException {
        Selection userSelections = favoriteRepository.findByUserId(userId);
       Selection updatedSelection;
        if(userSelections==null){
            throw new NoFavoriteGifFoundException();
        }
        else{
            if(userSelections.getFavoriteList().contains(gifUrl)){
                userSelections.getFavoriteList().remove(gifUrl);
                if(userSelections.getFavoriteList().isEmpty()){
                    favoriteRepository.deleteByUserId(userId);
                    updatedSelection= new Selection(userId, new HashSet<String>());
                }
                else {
                    updatedSelection = favoriteRepository.save(userSelections);
                }
                return updatedSelection.getFavoriteList();
            }
            else{
                throw new GifNotFoundException();
            }
        }
    }


    @Override
    public HashSet<String> emptyFavoriteList(int userId) throws NoFavoriteGifFoundException {
        if(!favoriteRepository.existsByUserId(userId)){
            throw new NoFavoriteGifFoundException();
        }
        else{
            HashSet<String> h = favoriteRepository.findByUserId(userId).getFavoriteList();
            if(h.isEmpty()){
                throw new NoFavoriteGifFoundException();
            }
            else{
                favoriteRepository.deleteByUserId(userId);
                LOG.info("favorite list successfully deleted");
            }
            return new HashSet<String>();
        }
    }

    @Override
    public HashSet<String> getAllFavorites(int userId)  throws NoFavoriteGifFoundException {
        if (!favoriteRepository.existsByUserId(userId)) {
            throw new NoFavoriteGifFoundException();
        } else {
            HashSet<String> h = favoriteRepository.findByUserId(userId).getFavoriteList();
            LOG.info("done");
            return h;

        }
    }

    @Override
    public boolean checkIfFavoriteByUrl(int userId, String gifUrl) throws NoFavoriteGifFoundException{
        if(!favoriteRepository.existsByUserId(userId)){
            throw new NoFavoriteGifFoundException();
        }
        else{
            HashSet<String> list = favoriteRepository.findByUserId(userId).getFavoriteList();
            return list.contains(gifUrl);
        }

    }
}
