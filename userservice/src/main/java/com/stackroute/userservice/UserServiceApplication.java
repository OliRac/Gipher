package com.stackroute.userservice;

import com.stackroute.userservice.controller.UserController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;

@SpringBootApplication(scanBasePackages="com.stackroute.userservice")
@ComponentScan(basePackages={"com.stackroute.userservice"})
@EnableJpaRepositories(basePackages="com.stackroute.userservice.repository")
@EntityScan(basePackages="com.stackroute.userservice.entity")
@EnableAutoConfiguration
public class UserServiceApplication {

	public static void main(String[] args) {
		new File(UserController.uploadDirectory).mkdir();

		SpringApplication.run(UserServiceApplication.class, args);
	}

}
