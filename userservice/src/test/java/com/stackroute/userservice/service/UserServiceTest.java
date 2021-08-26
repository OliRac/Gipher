package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

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

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @BeforeEach
    public void setUp(){
        user = new User();
        user.setUserId(-1);
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

    @Test
    public void givenUserToUpdatePhotoThenShouldReturnUpdatedUserWithPhotoPath() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        User retrievedUSer = userService.updatedUserPhotoPath(user, "test");
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    public void givenUserToFindByUsernameThenShouldReturnUserWithUsername() {
        when(userRepository.findUserByUsername(anyString())).thenReturn(user);
        User retrievedUser = userService.findUserByUsername("test");
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }

    @Test
    public void givenUserToFindByUsernameNotFoundThenShouldNotReturnUserWithUsername() throws UserNotFoundException{
        when(userRepository.findUserByUsername(anyString())).thenThrow(new UserNotFoundException());
        Assertions.assertThrows(UserNotFoundException.class, () ->
                userService.findUserByUsername("test"));
        verify(userRepository, times(1)).findUserByUsername(anyString());
    }

    @Test
    public void givenMultiPartFileThenShouldConvertToFile(){
        MockMultipartFile multipartFile = new MockMultipartFile("img", "testFile", "image/jpeg", "testFile".getBytes());
        File convertedFile = userService.convertMultiPartFileToFile(multipartFile);
        assertEquals(convertedFile.getName(), "testFile");
    }

}
