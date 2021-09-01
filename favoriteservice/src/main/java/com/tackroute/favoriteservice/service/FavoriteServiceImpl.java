package com.tackroute.favoriteservice.service;

import com.tackroute.favoriteservice.domain.UserGifDto;
import com.tackroute.favoriteservice.exception.GifAlreadyExistException;
import com.tackroute.favoriteservice.exception.GifNotFoundException;
import com.tackroute.favoriteservice.exception.NoFavoriteGifFoundException;
import com.tackroute.favoriteservice.domain.Selection;
import com.tackroute.favoriteservice.repository.FavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

// @Slf4j
@Service
public class FavoriteServiceImpl implements FavoriteService{

    private final Logger LOG = LoggerFactory.getLogger(getClass());


    private FavoriteRepository favoriteRepository;

   @Autowired
   public FavoriteServiceImpl(FavoriteRepository favoriteRepository) {this.favoriteRepository = favoriteRepository;}

    @Override
    public Selection addFavorite(UserGifDto userGifDto) throws GifAlreadyExistException {
//        System.out.println(favoriteRepository.existsByUserId(userId));

        Selection userSelections = favoriteRepository.findByUserId(userGifDto.getUserId());

        if(userSelections !=null) {
            if(userSelections.getFavoriteList().contains(userGifDto.getGifUrl())){
                throw new GifAlreadyExistException();
            }
            else {
                userSelections.getFavoriteList().add(userGifDto.getGifUrl());
            }
        } else {

                var set = new HashSet<String>();
                set.add(userGifDto.getGifUrl());
                userSelections = new Selection(userGifDto.getUserId(), set);

        }
        LOG.info("The Gif was successfully added to the favorite list of user" );
        return favoriteRepository.save(userSelections);

    }


    @Override
    public HashSet<String> removeFavorite(UserGifDto userGifDto) throws GifNotFoundException, NoFavoriteGifFoundException {
        Selection userSelections = favoriteRepository.findByUserId(userGifDto.getUserId());
       Selection updatedSelection;
        if(userSelections==null){
            throw new NoFavoriteGifFoundException();
        }
        else{
            if(userSelections.getFavoriteList().contains(userGifDto.getGifUrl())){
                userSelections.getFavoriteList().remove(userGifDto.getGifUrl());
                if(userSelections.getFavoriteList().isEmpty()){
                    favoriteRepository.deleteByUserId(userGifDto.getUserId());
                    updatedSelection= new Selection(userGifDto.getUserId(), new HashSet<String>());
                }
                else {
                    updatedSelection = favoriteRepository.save(userSelections);
                }
                LOG.info("The Gif was successfully removed from the favorite list of user" );
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
                LOG.info("favorite list successfully deleted for the user");
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
            LOG.info("list of favorite gifs was successfully retreived");
            return h;

        }
    }

    @Override
    public boolean checkIfFavoriteByUrl(UserGifDto userGifDto) throws NoFavoriteGifFoundException{
        if(!favoriteRepository.existsByUserId(userGifDto.getUserId())){
            throw new NoFavoriteGifFoundException();
        }
        else{
            HashSet<String> list = favoriteRepository.findByUserId(userGifDto.getUserId()).getFavoriteList();
            return list.contains(userGifDto.getGifUrl());
        }

    }
}
