package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserService {
    /**
     * AbstractMethod to save a user
     */
    User registerUser(User user) throws UserAlreadyExistException;

    /**
     * AbstractMethod to update a user
     */
    User updatedUserPhotoPath(User user, String filename);

    /**
     * AbstractMethod to find a user by username
     */
    User findUserByUsername(String filename);

    /**
     * AbstractMethod to convert MultipartFile to File
     */
    File convertMultiPartFileToFile(MultipartFile file);



}
