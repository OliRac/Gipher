package com.stackroute.userservice.controller;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
public class UserController {

    private UserService userService;
    ResponseEntity<?> responseEntity;
    public static String uploadDirectory = System.getProperty("user.dir") + "/webapp/assets/images";

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Save a new user
     */

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@ModelAttribute("user") User user, @RequestParam("img") MultipartFile file) throws IOException, UserAlreadyExistException {
        try {
            User savedUser = userService.registerUser(user);
            String filename = "";
            //id + gets the .jpg or .png image
            if(file != null && savedUser != null){
                filename = savedUser.getUserId() + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);
            }

            //Moving the file into the image directory
            Path fileNameAndPath = Paths.get(uploadDirectory, filename);
            if(file != null && savedUser != null){
                Files.write(fileNameAndPath, file.getBytes());
            }

            userService.updatedUserPhotoPath(savedUser, filename);

            responseEntity = new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        }
        catch (UserAlreadyExistException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
        catch(IOException e){
            e.printStackTrace();
        }

        return responseEntity;
    }

}
