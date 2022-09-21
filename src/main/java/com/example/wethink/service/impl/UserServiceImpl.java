package com.example.wethink.service.impl;

import com.example.wethink.dao.UserMapper;
import com.example.wethink.dto.RegisterDto;
import com.example.wethink.dto.Result;
import com.example.wethink.model.User;
import com.example.wethink.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByNameOrEmail(String loginName) {
        return userMapper.findUserByNameOrEmail(loginName);
    }
    @Override
    public Result register(RegisterDto registerDto) {
        User userQueryByName = userMapper.findUserByName(registerDto.getUsername());
        User userQueryByEmail = userMapper.findUserByEmail(registerDto.getEmail());
        if(userQueryByName != null){
            return new Result(HttpStatus.CONFLICT.value(),"用户名已经存在！");
        }
        if(userQueryByEmail != null){
            return new Result(HttpStatus.CONFLICT.value(),"邮箱已经存在！");
        }
        User user = new User();
        BeanUtils.copyProperties(registerDto, user);
        //使用spring-security加密
        String password = user.getPassword();
        password = new BCryptPasswordEncoder().encode(password);
        user.setPassword(password);
        user.setId(UUID.randomUUID().toString());
        userMapper.saveUser(user);
        return new Result(HttpStatus.OK.value(),"注册成功!");
    }
}
