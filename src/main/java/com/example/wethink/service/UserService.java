package com.example.wethink.service;

import com.example.wethink.dto.RegisterDto;
import com.example.wethink.dto.Result;
import com.example.wethink.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByNameOrEmail(String loginName);
    Result register(RegisterDto registerDto);
}
