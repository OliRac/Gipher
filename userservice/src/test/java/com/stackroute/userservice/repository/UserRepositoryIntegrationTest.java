package com.stackroute.userservice.repository;

import com.stackroute.userservice.controller.UserController;
import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.GlobalExceptionHandler;
import com.stackroute.userservice.service.UserService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    private MockMvc mockMvc;

    @Mock
    private UserService userService2;

    @InjectMocks
    private UserController userController;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();

        user = new User();
        user.setUserId(1);
        user.setUsername("leo");
        user.setPassword("leo123");
        user.setPhoto("7.jpg");
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        user = null;
    }

    @Test
    public void givenUserToSaveThenShouldReturnSavedUser() {
        userRepository.save(user);
        User fetchedUser = userRepository.findById(user.getUserId()).get();
        assertEquals(1, fetchedUser.getUserId());
    }

}
