package com.stackroute.userservice.repository;

import com.stackroute.userservice.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp(){
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
        User findUser = userRepository.save(user);
        assertEquals(user.getUsername(), findUser.getUsername());
    }


}
