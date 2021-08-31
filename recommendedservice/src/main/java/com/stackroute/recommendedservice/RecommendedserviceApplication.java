package com.stackroute.recommendedservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableRabbit
@EnableCaching
@SpringBootApplication
public class RecommendedserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendedserviceApplication.class, args);
	}

}
