package com.wethink.controller;

import com.wethink.dto.CommentsDto;
import com.wethink.dto.Result;
import com.wethink.service.CommentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    //todo
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CommentsService commentsService;

    @GetMapping("/findAll")
    public Result findAll(){
        return commentsService.findCommentsAll();
    }

    @GetMapping("/findByParentId")
    public Result findByParentId(@RequestParam String parentId){
        return commentsService.findCommentsByParentId(parentId);
    }

    @GetMapping("/findByUserId")
    public Result findAllByUserId(@RequestParam String userId){
        return commentsService.findCommentsByUserId(userId);
    }

    @PostMapping("/save")
    public Result save(@RequestBody @Validated CommentsDto commentsDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            List<String> errorList = new ArrayList();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
            return new Result(HttpStatus.BAD_REQUEST.value(), errorList.toString());
        }
        return commentsService.saveComments(commentsDto);
    }
}
