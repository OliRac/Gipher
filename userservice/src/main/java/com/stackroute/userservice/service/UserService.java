package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;

public interface UserService {
    /**
     * AbstractMethod to save a user
     */
    User registerUser(User user) throws UserAlreadyExistException;

    /**
     * AbstractMethod to update a user
     */
    User updatedUserPhotoPath(User user, String filename);



}
