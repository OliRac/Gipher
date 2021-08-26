package com.stackroute.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.GlobalExceptionHandler;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.service.UserService;
import com.stackroute.userservice.utility.JwtUtil;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    UserService userService;

    @Mock
    JwtUtil jwtUtill;

    @Mock
    PasswordEncoder passwordEncoder;

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
                multipart("/auth/register")
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
                multipart("/auth/register")
                        .file(file1)
                        .file(file2)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void givenUserToLoginThenShouldNotReturnTokenAndLoginUser() throws Exception {
        when(userService.findUserByUsername(any())).thenThrow(UserAlreadyExistException.class);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isConflict())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void givenUserToLoginThenShouldReturnTokenAndLoginUser() throws Exception {
        when(userService.findUserByUsername(any())).thenReturn(user);
        when(jwtUtill.generateToken(any())).thenReturn("testtoken");
        when(passwordEncoder.matches(any(), any())).thenReturn(true);
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(user)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
