package dao;

import com.mlh.dao.impl.UserDAOImpl;
import com.mlh.model.UserPO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/17
 * @description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "spring.main.web-application-type=reactive",classes = com.SpringbootMybatisDemoApplication.class)
public class TestUserDAO{

        private static final Logger logger = LogManager.getLogger(TestUserDAO.class);

        @Resource
        private UserDAOImpl dao;

        @Test
        public void testXxxById() {
            String UserId = "C00018";
            List<UserPO> list = dao.queryById(UserId);
            Assert.assertNotNull(list);
            logger.info("");
        }
    }
