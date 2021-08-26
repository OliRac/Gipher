package com.stackroute.recommendedservice.repository;

import com.stackroute.recommendedservice.entity.UserTerms;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTermsRepositoryTest {
    @Autowired
    private UserTermsRepository userTermsRepository;

    private List<UserTerms> userTermsList;

    @BeforeEach
    void setUp() {
        userTermsList = List.of();
        userTermsRepository.saveAll(userTermsList);
    }

    @AfterEach
    void tearDown() {
    }
}