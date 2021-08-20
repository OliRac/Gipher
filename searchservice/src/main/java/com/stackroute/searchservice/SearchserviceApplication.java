package com.stackroute.searchservice;

import com.stackroute.searchservice.model.Gif;
import com.stackroute.searchservice.model.SearchEngine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@Slf4j
//@EnableAutoConfiguration
public class SearchserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SearchserviceApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public SearchEngine getSearchEngin(){
		return new SearchEngine();
	}
	}



