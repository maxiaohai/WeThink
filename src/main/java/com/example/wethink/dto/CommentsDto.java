package com.example.wethink.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class CommentsDto {

    private String parentId;
    @NotEmpty
    private String userId;
    @Size(min = 3, max = 200, message = "评论长度在3~200之间!")
    private String username;
    private String content;
}
