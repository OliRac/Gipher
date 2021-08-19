package com.stackroute.userservice.controller;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerIntegrationTest {
    @Autowired
    private UserService userService;

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
