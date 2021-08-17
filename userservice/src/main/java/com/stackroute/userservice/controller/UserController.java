package com.stackroute.userservice.controller;

import com.stackroute.userservice.entity.User;
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

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    public static String uploadDirectory = System.getProperty("user.dir") + "/webapp/assets/images";

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    /**
     * Save a new user
     */
    @PostMapping("/user")
    public ResponseEntity<User> saveBlog(@ModelAttribute User user, @RequestParam("img") MultipartFile file) throws IOException {

        User savedUser = userService.saveUser(user);
        //id + gets the .jpg or .png image
        String filename = savedUser.getUserId() + file.getOriginalFilename().substring(file.getOriginalFilename().length()-4);

        //Moving the file into the image directory
        Path fileNameAndPath = Paths.get(uploadDirectory, filename);

        try {
            Files.write(fileNameAndPath, file.getBytes());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        userService.updatedUserPhotoPath(savedUser, filename);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

}
