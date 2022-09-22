package com.wethink.service;

import com.wethink.dto.RegisterDto;
import com.wethink.dto.Result;
import com.wethink.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByNameOrEmail(String loginName);
    Result register(RegisterDto registerDto);
}
