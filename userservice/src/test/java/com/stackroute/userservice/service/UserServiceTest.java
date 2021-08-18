package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

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
    public void tearDown() {
        user = null;
    }

    @Test
    void givenUserToSaveThenShouldReturnSavedUser() throws UserAlreadyExistException {
        when(userRepository.save(any())).thenReturn(user);
        assertEquals(user, userService.registerUser(user));
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void givenUserToSaveThenShouldNotReturnSavedUser() throws UserAlreadyExistException {
        when(userRepository.save(user)).thenThrow(new UserAlreadyExistException());
        Assertions.assertThrows(UserAlreadyExistException.class, () ->
                userService.registerUser(user));
        verify(userRepository, times(1)).save(user);
    }

}
