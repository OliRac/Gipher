package com.stackroute.searchservice.service;

import com.stackroute.searchservice.model.Gif;
import com.stackroute.searchservice.repository.GifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GifServiceImpl implements GifService{

    private final GifRepository gifRepository;
    @Autowired
    public GifServiceImpl(GifRepository gifRepository){
        this.gifRepository = gifRepository;
    }

    @Override
    public List<Gif> getAllGifs() {
        return gifRepository.findAll();
    }

    @Override
    public List<Gif> getGifByTitle(String title) {
        return gifRepository.findByTitle(title);
    }


}
