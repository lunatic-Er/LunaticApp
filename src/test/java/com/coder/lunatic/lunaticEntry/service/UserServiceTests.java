package com.coder.lunatic.lunaticEntry.service;

import com.coder.lunatic.lunaticEntry.entity.User;
import com.coder.lunatic.lunaticEntry.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class UserServiceTests {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository repo;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void saveNewUserTest(){
        User user = new User();
        user.setUserName("abcd");
        user.setPassword("abcd");
        Assertions.assertTrue(userService.saveNewUser(user));
//        Assertions.assertThrows(NullPointerException.class,() -> userService.saveUser(user));
    }

}
