package com.mlh.dao.impl;

import com.mlh.mapper.UserMapper;
import com.mlh.model.UserPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/17
 * @description:
 */
@Repository
public class UserDAOImpl {
    @Resource
    UserMapper userMapper;

    public List<UserPO> queryById(String userId) throws DataAccessException {
        return userMapper.queryById(userId);
    }
}

