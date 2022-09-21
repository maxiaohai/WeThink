package com.example.wethink.dao;

import com.example.wethink.model.User;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * tb_users表数据库访问层
 */
@Mapper
public interface UserMapper {
    User findUserById(String id);
    User findUserByName(String userName);
    User findUserByEmail(String email);
    User findUserByNameOrEmail(String loginName);
    List<User> findUserAll();
    void saveUser(User user);
}