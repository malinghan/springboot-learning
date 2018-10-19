package dao;

import com.mlh.App;
import com.mlh.dao.UserDao;
import com.mlh.model.User;
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

    @Test
    public void findUserById() {
        User user = userDao.findUserById(3);
        Assert.assertNotNull("没找到数据", user);
    }

}