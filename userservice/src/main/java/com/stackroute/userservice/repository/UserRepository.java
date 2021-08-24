package com.stackroute.userservice.repository;

import com.stackroute.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findUserByUsername(String username);
}
