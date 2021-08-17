package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;

public interface UserService {
    /**
     * AbstractMethod to save a user
     */
    User saveUser(User user);

    /**
     * AbstractMethod to update a user
     */
    User updatedUserPhotoPath(User user, String filename);



}
