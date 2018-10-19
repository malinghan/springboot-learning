package com.mlh.dao;

import java.util.List;
import com.mlh.model.User;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.springframework.stereotype.Repository;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/12
 * @description:
 */
@Repository
public interface UserDao {

     void insert(User user);


     User findUserById (int userId);

     List<User> findAllUsers();

}