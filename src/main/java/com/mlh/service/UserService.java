package com.mlh.service;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/17
 * @description:
 */
import com.github.pagehelper.PageInfo;
import com.mlh.model.UserPO;

/**
 * Created by Administrator on 2018/4/19.
 */
public interface UserService {

    int addUser(UserPO user);

    PageInfo<UserPO> findAllUser(int pageNum, int pageSize);
}