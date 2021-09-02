package com.stackroute.recommendedservice;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableRabbit
@EnableCaching
@SpringBootApplication
@EnableEurekaClient
public class RecommendedserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommendedserviceApplication.class, args);
	}

}
