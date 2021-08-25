package com.stackroute.searchservice.controller;

import com.stackroute.searchservice.model.Gif;
import com.stackroute.searchservice.service.ParsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
@Slf4j
@Controller
public class GifController {
    private static final String SEARCH_RESULT_PAGE ="result";
    private static final String JSON_GIF_URL = "https://g.tenor.com/v1/search?q=book&key=4Y44R27LR14Q&limit=1";

    @Autowired
    private ParsService parsService;

//    @GetMapping
//    public String  getGif(final Model model){
//        List<Gif> gifs = (List<Gif>) parsService.parse(JSON_GIF_URL);
//        model.addAttribute(gifs.get(0));
//        log.info("Gif URL - : " + gifs.stream().findAny());
//        return SEARCH_RESULT_PAGE;
//    }
}
