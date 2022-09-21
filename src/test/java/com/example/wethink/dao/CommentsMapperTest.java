package com.example.wethink.dao;

import com.example.wethink.model.Comments;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsMapperTest {

    @Autowired
    private CommentsMapper commentsMapper;

    @Test
    public void findCommentsByParentId() {
        Assert.assertNotNull(commentsMapper.findCommentsByParentId("8d790940-42ca-4dd6-9c71-8218d2f00759"));
    }

    @Test
    public void findCommentsByUserId() {
        Assert.assertNotNull(commentsMapper.findCommentsByUserId("8d790940-42ca-4dd6-9c71-8218d2f00759"));
    }

    @Test
    public void findCommentsAll() {
        List<Comments> commentsList = commentsMapper.findCommentsAll();
        Assert.assertNotEquals(0, commentsList.size());
    }

    @Test
    public void saveComments() {
        Comments comments = new Comments();
        comments.setId(UUID.randomUUID().toString());
        comments.setContent("测试评论区！");
        comments.setUserId("44149fb4-00b4-4f5f-9278-90210978e969");
        comments.setCreateTime(new Date());
        commentsMapper.saveComments(comments);
    }
}