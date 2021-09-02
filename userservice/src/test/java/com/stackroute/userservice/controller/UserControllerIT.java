package com.stackroute.userservice.controller;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.GlobalExceptionHandler;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.repository.UserRepository;
import com.stackroute.userservice.service.UserService;
import com.stackroute.userservice.service.UserServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerIT {

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
        userRepository.deleteAll();
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
        newUser.setUserId(-2);
        newUser.setUsername("hey");
        newUser.setPassword("hey123");
        assertNotNull(userService.registerUser(newUser));
    }

}
