package com.stackroute.searchservice.service;

import com.stackroute.searchservice.exception.UserNotFoundException;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class SearchEngineServiceImpl implements SearchEngineService{
    @Autowired
    SearchRepository searchRepository;

    public SearchEngineServiceImpl(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    /***
     * Saves the searchEngine object into db.
     * It checks if the userId exists, if so it will add the searchTerm
     * @param userId
     * @param searchTerm
     * @return searchEngine object
     */
    @Override
    public SearchEngine addSearchInfo(int userId, String searchTerm) throws UserNotFoundException {
        log.info("Adding search info into db");
        SearchEngine searchInfo = searchRepository.findByUserId(userId);
        if(searchInfo != null) {
            searchInfo.getSearchTermSet().add(searchTerm);
        }else{
            Set<String> searchSet = new HashSet<String>();
            searchSet.add(searchTerm);
            searchInfo = new SearchEngine(userId , searchSet);

        }
        return searchRepository.save(searchInfo);

    }

    @Override
    public List<SearchEngine> getAllSearch() {
        return searchRepository.findAll();
    }

    @Override
    public SearchEngine updateSearchInfo(SearchEngine searchEngine) {
        return null;
    }

}
