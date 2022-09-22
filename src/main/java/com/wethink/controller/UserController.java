package com.wethink.controller;

import com.wethink.dto.RegisterDto;
import com.wethink.dto.Result;
import com.wethink.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

   @PostMapping(value = "/register")
   @ResponseBody
    public Result register(@RequestBody @Validated RegisterDto registerDto, BindingResult bindingResult){
       if (bindingResult.hasErrors()) {
           List<String> errorList = new ArrayList();
           for (ObjectError error : bindingResult.getAllErrors()) {
               errorList.add(error.getDefaultMessage());
           }
           return new Result(HttpStatus.BAD_REQUEST.value(), errorList.toString());
       }
       return userService.register(registerDto);
    }
}
