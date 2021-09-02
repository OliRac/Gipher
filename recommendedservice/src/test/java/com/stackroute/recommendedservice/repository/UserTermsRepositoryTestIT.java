package com.stackroute.recommendedservice.repository;

import com.stackroute.recommendedservice.entity.UserTerms;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTermsRepositoryTestIT {
    @Autowired
    private UserTermsRepository userTermsRepository;

    private UserTerms ut1;
    private UserTerms ut2;
    private UserTerms ut3;

    @BeforeEach
    void setUp() {
        ut1 = new UserTerms();
        ut1.setUserId(1);
        ut1.setTerms(Map.of("one", 1));

        ut2 = new UserTerms();
        ut2.setUserId(2);
        ut2.setTerms(Map.of("two", 2));

        ut3 = new UserTerms();
        ut3.setUserId(3);
        ut3.setTerms(Map.of("three", 3));

        userTermsRepository.save(ut1);
        userTermsRepository.save(ut2);
        userTermsRepository.save(ut3);
    }

    @AfterEach
    void tearDown() {
        userTermsRepository.deleteAll();
    }

    @Test
    public void findUserTermsByUserIdShouldReturnUserTerms() {
        var result = userTermsRepository.findUserTermsByUserId(1);
        assertEquals(ut1.getUserId(), result.getUserId());
    }

    @Test
    public void findUserTermsByUserIdShouldNullForNonExistentUser() {
        var result = userTermsRepository.findUserTermsByUserId(5);
        assertNull(result);
    }
}