package com.stackroute.searchservice.service;

import com.stackroute.searchservice.exception.UserNotFoundException;
import com.stackroute.searchservice.model.SearchEngine;
import com.stackroute.searchservice.repository.SearchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class SearchEngineServiceImpl implements SearchEngineService{
    @Autowired
    SearchRepository searchRepository;
    @Autowired
    SearchEngine searchEngine1;

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
        SearchEngine search = null;
        Optional optional = searchRepository.findById(userId);

        if(optional.isPresent()){
            search = searchRepository.findById(userId).get();
            log.info("search for the user id " + userId + "exists so adding searchTerm to the set");
            Set<String> searchList = search.getSearchTermSet();
            searchList.add(searchTerm);
            search.setSearchTermSet(searchList);
            return searchRepository.save(search);
        }
//        SearchEngine search = searchRepository.findById(userId).orElseThrow(UserNotFoundException::new);
//        SearchEngine search = new SearchEngine();
        search = new SearchEngine();
        search.setUserId(userId);
        search.getSearchTermSet().add(searchTerm);
        search.setSearchTermSet(search.getSearchTermSet());
        return searchRepository.save(search);


    }

    @Override
    public List<SearchEngine> getAllSearch() {
        return searchRepository.findAll();
    }

    @Override
    public SearchEngine updateSearchInfo(SearchEngine searchEngine) {
        return null;
    }

//    @Override
//    public SearchEngine getSearchInfoByUserId(int userId) {
//        return searchRepository.findById(userId).orElseThrow();
//    }
}
