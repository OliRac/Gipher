package com.stackroute.userservice.controller;

import com.stackroute.userservice.entity.JwtRequest;
import com.stackroute.userservice.entity.JwtResponse;
import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.service.UserService;
import com.stackroute.userservice.utility.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    ResponseEntity<?> responseEntity;
    public static String uploadDirectory = System.getProperty("user.dir") + "/webapp/assets/images";
    private static Logger logger = LoggerFactory.getLogger(UserController.class);


    @Autowired
    public UserController(UserService userService){
        this.userService = userService;

    }

    /**
     * Save a new user
     */
    @PostMapping("/auth/register")
    public ResponseEntity<?> registerUser(@ModelAttribute("user") User user, @RequestParam("img") MultipartFile file) throws IOException, UserAlreadyExistException {
        try {
            User savedUser = userService.registerUser(user);

            //id + gets the .jpg or .png image
            String filename = savedUser.getUserId() + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);

            //Moving the file into the image directory
            Path fileNameAndPath = Paths.get(uploadDirectory, filename);
            if(file != null && savedUser != null && savedUser.getUserId() != -1){
                Files.write(fileNameAndPath, file.getBytes());
            }

            userService.updatedUserPhotoPath(savedUser, filename);

            responseEntity = new ResponseEntity<>(savedUser, HttpStatus.OK);

            logger.info("User created successfully!");
        }
        catch (UserAlreadyExistException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            logger.info("User already exists!!");
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return responseEntity;
    }

    /**
     * Authenticate user and return a json token if valid
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        User findUser = userService.findUserByUsername(jwtRequest.getUsername());

        boolean isValidPw = passwordEncoder.matches(jwtRequest.getPassword(), findUser.getPassword());

        if(findUser == null || !isValidPw){
            throw new UserNotFoundException("User Not Found");
        }

        final String token = jwtUtil.generateToken(findUser.getUsername());

        return ResponseEntity.ok(new JwtResponse(token, findUser));
    }

}
