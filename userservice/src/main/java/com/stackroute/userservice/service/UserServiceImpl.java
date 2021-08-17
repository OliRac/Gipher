package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;

    /**
     * Constructor based Dependency injection to inject UserRepository here
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updatedUserPhotoPath(User user, String filename) {
        User updatedUser = userRepository.findById(user.getUserId()).get();
        updatedUser.setPhoto(filename);
        return userRepository.save(updatedUser);
    }

}
