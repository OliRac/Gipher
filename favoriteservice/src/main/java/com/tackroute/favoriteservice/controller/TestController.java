package com.tackroute.favoriteservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestController {
    @GetMapping("favorite-service/hello")
    public String firstPage() {
        return "Hello World";
    }
}
