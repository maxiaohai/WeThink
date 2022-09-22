package com.wethink.dao;

import com.wethink.model.Comments;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * tb_comments表数据库访问层
 */
@Mapper
public interface CommentsMapper {
    List<Comments> findCommentsByParentId(String parentId);
    List<Comments> findCommentsByUserId(String userId);
    List<Comments> findCommentsAll();
    void saveComments(Comments comments);
}