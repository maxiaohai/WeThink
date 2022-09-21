package com.example.wethink.service.impl;

import com.example.wethink.dto.RegisterDto;
import com.example.wethink.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    public void findUserById() {
    }

    @Test
    public void findUserAll() {
    }

    @Test
    public void register() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("testUser");
        registerDto.setEmail("14032423@qq.com");
        registerDto.setPassword("Qw123@er?0");
        userService.register(registerDto);
    }
}