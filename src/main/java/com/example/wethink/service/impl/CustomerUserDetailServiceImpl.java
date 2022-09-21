package com.example.wethink.service.impl;

import com.example.wethink.model.SecurityUser;
import com.example.wethink.model.User;
import com.example.wethink.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomerUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = new SecurityUser();
        User user = userService.findByNameOrEmail(username);
        if (user == null){
            throw new UsernameNotFoundException("username " + username + " not found");
        }
        BeanUtils.copyProperties(user, securityUser);
        return securityUser;
    }
}
