package com.tackroute.favoriteservice.controller;

import com.tackroute.favoriteservice.model.Selection;
import com.tackroute.favoriteservice.repository.FavoriteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class FavoriteController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());


    private FavoriteRepository favoriteRepository;

    @Autowired
    public FavoriteController(FavoriteRepository favoriteRepository){
        this.favoriteRepository =favoriteRepository;
    }

    @RequestMapping(value = "/favorites/{userId}", method = RequestMethod.GET)
    public Selection getAllFavorites(@PathVariable int userId) {
        LOG.info("Getting user with ID: {}.", userId);
        return favoriteRepository.findById(userId).get();
    }

    @RequestMapping(value = "/createFavoriteList/{userId}", method = RequestMethod.POST)
    public Selection addFirstFavorite(@RequestBody Selection selection) {
        LOG.info("Saving user.");
        return favoriteRepository.save(selection);
    }

    @RequestMapping(value = "/addFavorite/{userId}/{gifUrl}", method = RequestMethod.GET)
    public String addUserSetting(@PathVariable int userId, @PathVariable String gifUrl) {
        Selection selection = favoriteRepository.findById(userId).get();
        if (user != null) {
            user.getUserSettings().put(key, value);
            userRepository.save(user);
            return "Key added";
        } else {
            return "User not found.";
        }
    }










}
