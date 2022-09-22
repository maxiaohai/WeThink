package com.wethink.service.impl;

import com.wethink.dto.CommentsDto;
import com.wethink.dto.Result;
import com.wethink.service.CommentsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsSeviceImplTest {

    @Autowired
    private CommentsService commentsService;

    @Test
    public void findCommentsAll() {
        Assert.assertNotNull(commentsService.findCommentsAll());
    }

    @Test
    public void saveComments() {
        CommentsDto commentsDto = new CommentsDto();

        commentsDto.setContent("测试saveComments!");
        commentsDto.setUserId("8d790940-42ca-4dd6-9c71-8218d2f00759");
        Result result = commentsService.saveComments(commentsDto);
        Assert.assertEquals(200, result.getCode().intValue());

    }

    @Test
    public void findCommentsByParentId() {
        Result result = commentsService.findCommentsByParentId("8d790940-42ca-4dd6-9c71-8218d2f00759");
        Assert.assertEquals(200, result.getCode().intValue());
    }


    @Test
    public void findCommentsByUserId() {
        Result result = commentsService.findCommentsByUserId("8d790940-42ca-4dd6-9c71-8218d2f00759");
        Assert.assertEquals(200, result.getCode().intValue());
    }

}