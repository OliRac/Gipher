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
        System.out.println(favoriteRepository.existsByUserId(userId));

        Selection userSelections = favoriteRepository.findByUserId(userId);

        if(userSelections != null) {
            userSelections.getFavoriteList().add(gifUrl);
        } else {
            var set = new HashSet<String>();
            set.add(gifUrl);
            userSelections = new Selection(userId, set);
        }

        return favoriteRepository.save(userSelections);
   }


    @Override
    public HashSet<String> removeFavorite(int userId, String gifUrl) throws GifNotFoundException {
        Selection updatedSelection;
        if(!favoriteRepository.existsById(userId)){
            throw new GifNotFoundException();
        }
        else{
            HashSet<String> h = favoriteRepository.findById(userId).get().getFavoriteList();
            if(h.contains(gifUrl)){
                h.remove(gifUrl);
                if(h.isEmpty()){
                    favoriteRepository.deleteById(userId);
                    updatedSelection= new Selection(userId, new HashSet<String>());
                }
                else {
                    updatedSelection = favoriteRepository.save(new Selection(userId, h));
                }
            }
            else{
                throw new GifNotFoundException();
            }
        }
        return updatedSelection.getFavoriteList();
    }


    @Override
    public HashSet<String> emptyFavoriteList(int userId) throws NoFavoriteGifFoundException {
        if(!favoriteRepository.existsById(userId)){
            throw new NoFavoriteGifFoundException();
        }
        else{
            HashSet<String> h = favoriteRepository.findById(userId).get().getFavoriteList();
            if(h.isEmpty()){
                throw new NoFavoriteGifFoundException();
            }
            else{
                favoriteRepository.deleteById(userId);
                LOG.info("favorite list successfully deleted");
            }
            return new HashSet<String>();
        }
    }

    @Override
    public HashSet<String> getAllFavorites(int userId)  throws NoFavoriteGifFoundException {
        if (!favoriteRepository.existsById(userId)) {
            throw new NoFavoriteGifFoundException();
        } else {
            HashSet<String> h = favoriteRepository.findById(userId).get().getFavoriteList();
            LOG.info("done");
            return h;

        }
    }

    @Override
    public boolean checkIfFavoriteByUrl(int userId, String gifUrl) throws NoFavoriteGifFoundException{
        if(!favoriteRepository.existsById(userId)){
            throw new NoFavoriteGifFoundException();
        }
        else{
            HashSet<String> list = favoriteRepository.findById(userId).get().getFavoriteList();
            return list.contains(gifUrl);
        }

    }
}
