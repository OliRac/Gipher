package com.stackroute.userservice.service;

import com.stackroute.userservice.entity.User;
import com.stackroute.userservice.exception.UserAlreadyExistException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;


    /**
     * Constructor based Dependency injection to inject UserRepository here
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistException {
        User findUser = userRepository.findUserByUsername(user.getUsername());
        if (findUser != null) {
            throw new UserAlreadyExistException("User Already Exists");
        }
        else {
            if(user.getUserId() != -1 ) {
                user.setPassword(bcryptEncoder.encode(user.getPassword()));
            }
            return userRepository.save(user);
        }
    }

    @Override
    public User updatedUserPhotoPath(User user, String filename) {
        User updatedUser = userRepository.findById(user.getUserId()).get();
        updatedUser.setPhoto(filename);
        return userRepository.save(updatedUser);
    }

    @Override
    public User findUserByUsername(String username) {
        User findUser = userRepository.findUserByUsername(username);

        if (findUser == null) {
            throw new UserNotFoundException("User Not Found");
        }
        return findUser;
    }

    @Override
    public File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }

}
