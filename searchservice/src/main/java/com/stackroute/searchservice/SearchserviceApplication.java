package com.stackroute.searchservice;

import com.stackroute.searchservice.model.SearchEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@Slf4j
public class SearchserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SearchserviceApplication.class, args);
	}

	@Bean
	public SearchEngine getSearchEngin(){
		return new SearchEngine();
	}
	}



