package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.Gif;

import java.util.List;

public interface GifService {
    List<Gif> getAllGifs();
    List<Gif> getGifByTitle(String title);
}
