package com.wethink.dao;

import com.wethink.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void findUserById() {
        Assert.assertNotNull(userMapper.findUserById("8d790940-42ca-4dd6-9c71-8218d2f00759"));
    }
    @Test
    public void findUserByName() {
        Assert.assertNotNull(userMapper.findUserByName("xiaohai123"));
    }

    @Test
    public void findUserByEmail() {
        Assert.assertNotNull(userMapper.findUserByEmail("1403728947@qq.com"));
    }
    @Test
    public void findUserByNameOrEmail() {
        Assert.assertNotNull(userMapper.findUserByNameOrEmail("12455@163com"));
    }

    @Test
    public void save(){
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setUsername("xiaohai");
        user.setEmail("1403728947@qq.com");
        user.setPassword("123456");
        userMapper.saveUser(user);
    }
}


