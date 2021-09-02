package com.stackroute.userservice.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.stackroute.userservice.entity.JwtRequest;
import com.stackroute.userservice.entity.JwtResponse;
import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.InvalidPasswordException;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.service.UserService;
import com.stackroute.userservice.utility.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@RestController
public class UserController {

    @Value("${application.bucket.name:test}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    ResponseEntity<?> responseEntity;

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

            String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File fileObj = userService.convertMultiPartFileToFile(file);

            if(savedUser.getUserId() > 0){
                //add the image to the S3 Storage
                s3Client.putObject(new PutObjectRequest(bucketName, filename, fileObj));
                //make anyone have access to URL
                s3Client.setObjectAcl(bucketName, filename, CannedAccessControlList.PublicRead);
                //remove this object in the userservice application
                fileObj.delete();
                s3Client.getUrl(bucketName, filename);

                userService.updatedUserPhotoPath(savedUser, s3Client.getUrl(bucketName, filename).toString());
            }

            responseEntity = new ResponseEntity<>(savedUser, HttpStatus.OK);

            log.info("POST /auth/register - User created successfully!");
        }
        catch (UserAlreadyExistException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            log.info("POST /auth/register - User already exists!!");
        }

        return responseEntity;
    }

    /**
     * Authenticate user and return a json token if valid
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) {
        User findUser = userService.findUserByUsername(jwtRequest.getUsername());
        if(findUser == null){
            return new ResponseEntity<JwtResponse>(new JwtResponse("notvalidtoken", findUser), HttpStatus.CONFLICT);
        }
        boolean isValidPw = passwordEncoder.matches(jwtRequest.getPassword(), findUser.getPassword());

        if(!isValidPw) {
            log.info("POST /auth/login - Password is invalid");
            throw new InvalidPasswordException("POST /auth/login - Invalid Password");
        }

        final String token = jwtUtil.generateToken(findUser.getUsername());

        log.info("POST /auth/login - Token generated for valid user!");
        return ResponseEntity.ok(new JwtResponse(token, findUser));
    }

}
