package com.wethink.service.impl;

import com.wethink.dao.CommentsMapper;
import com.wethink.dto.CommentsDto;
import com.wethink.dto.Result;
import com.wethink.model.Comments;
import com.wethink.model.TreeNode;
import com.wethink.service.CommentsService;
import com.wethink.utils.TreeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentsSeviceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;


    @Override
    public Result findCommentsByParentId(String parentId) {
        List<Comments> commentsList = commentsMapper.findCommentsByParentId(parentId);
        return new Result<>(HttpStatus.OK.value(), "请求成功", commentsList);
    }

    @Override
    public Result findCommentsByUserId(String userId) {
        List<Comments> commentsList = commentsMapper.findCommentsByUserId(userId);
        return new Result<>(HttpStatus.OK.value(), "请求成功", commentsList);
    }

    @Override
    public Result findCommentsAll() {
        //1.根据文章id查询该文章下的所有评论.
        List<Comments> commentsList = commentsMapper.findCommentsAll();
        //2.创建parentId的一对多映射关系.
        Map<String, List<TreeNode>> commentsMap = TreeUtils.CommentsListToParentId(commentsList);
        //3.递归构建commentsTree的结构.
        TreeNode commentsTree = TreeUtils.CommentsListToCommentsTree(commentsMap);
        return new Result<>(HttpStatus.OK.value(), "请求成功", commentsTree);
    }

    @Override
    public Result saveComments(CommentsDto commentsDto) {
        Comments comments = new Comments();
        BeanUtils.copyProperties(commentsDto, comments);
        comments.setId(UUID.randomUUID().toString());
        comments.setCreateTime(new Date());
        if(comments.getParentId().isEmpty()){
            comments.setParentId("root");
        }
        commentsMapper.saveComments(comments);
        return new Result<>(HttpStatus.OK.value(), "保存成功!");
    }
}
