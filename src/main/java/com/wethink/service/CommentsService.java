package com.wethink.service;

import com.wethink.dto.CommentsDto;
import com.wethink.dto.Result;
import org.springframework.stereotype.Service;

@Service
public interface CommentsService {
    Result findCommentsByParentId(String parrentId);
    Result findCommentsByUserId(String userId);
    Result findCommentsAll();
    Result saveComments(CommentsDto commentsDto);
}
