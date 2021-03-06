package com.stackroute.userservice.repository;

import com.stackroute.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsername(String username);
}
