package com.mlh.dao;

import com.mlh.App;
import com.mlh.config.DataSourceConfig;
import com.mlh.dao.UserDao;
import com.mlh.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/12
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserDao userDao2;

    @Test
    public void findUserById() {
        User user = userDao.findUserById(3);
        Assert.assertNotNull("没找到数据", user);
    }

    @Test
    public void  testFirstClassCache()  throws  Exception{
        // 获得SqlSession对象
        // 获得dao实体
        // 进行两次相同的查询操作
        userDao.findUserById(3);
        userDao.findUserById(3);
        userDao2.findAllUsers();
    }

}