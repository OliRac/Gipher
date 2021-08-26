package com.stackroute.userservice.controller;

import com.stackroute.userservice.UserServiceApplication;
import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.repository.UserRepository;
import com.stackroute.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setUserId(-1);
        user.setUsername("leo");
        user.setPassword("leo123");
        user.setPhoto("7.jpg");
    }

    @AfterEach
    public void tearDown(){
        user = null;
        userRepository.deleteAll();
    }

    @Test
    void givenUserToSaveThenThrowException() throws UserAlreadyExistException {
        assertNotNull(userService.registerUser(user));
        assertThrows(UserAlreadyExistException.class, () -> userService.registerUser(user));
    }

    @Test
    void givenUserToSaveThenShouldReturnTheSavedUser() throws UserAlreadyExistException {
        User newUser = new User();
        newUser.setUsername("hey");
        newUser.setPassword("hey123");
        assertNotNull(userService.registerUser(newUser));
    }

}
