package com.stackroute.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.GlobalExceptionHandler;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
        user = new User();
        user.setUserId(-1);
        user.setUsername("leo");
        user.setPassword("leo123");
    }

    @AfterEach
    public void tearDown(){
        user = null;
    }

    @Test
    void givenUserToSaveThenShouldReturnSavedUser() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("img", "testFile", "image/jpeg", "testFile".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("user", "user", "application/json", asJsonString(user).getBytes());

        when(userService.registerUser(any())).thenReturn(user);
        mockMvc.perform(
                multipart("/register")
                        .file(file1)
                        .file(file2)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    void givenUserToSaveThenShouldNotReturnSavedUser() throws Exception {
        MockMultipartFile file1 = new MockMultipartFile("img", "testFile", "image/png", "testFile".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("user", "user", "application/json", asJsonString(user).getBytes());

        when(userService.registerUser((User) any())).thenThrow(UserAlreadyExistException.class);
        mockMvc.perform(
                multipart("/register")
                        .file(file1)
                        .file(file2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
