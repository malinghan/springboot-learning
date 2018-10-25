package com.mlh.dao;

import java.util.List;

import com.mlh.Annotation.TargetDataSource;
import com.mlh.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/12
 * @description:
 */
@Repository
//@Mapper
public interface UserDao {

     void insert(User user);

     @TargetDataSource(name = "master")
     User findUserById (int userId);

     List<User> findAllUsers();
}