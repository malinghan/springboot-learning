package com.mlh.dao;

import com.mlh.App;
import com.mlh.config.DataSourceConfig;
import com.mlh.config.DynamicDataSource;
import com.mlh.dao.UserDao;
import org.apache.catalina.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.InputStream;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/24
 * @description:
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = App.class)
public class MybatisCacheTest {


    @Autowired
    private org.springframework.core.env.Environment env;

    /*
  * 一级缓存: 也就Session级的缓存(默认开启)
  */
    private SqlSessionFactory buildSqlSessionFactory() throws Exception{

      //创建数据源
        DataSource dataSource = new DynamicDataSource();
        //事务管理器
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        //数据源环境
        Environment environment = new Environment("master", transactionFactory, dataSource);

        Configuration configuration = new Configuration(environment);
        configuration.setLazyLoadingEnabled(true);
        configuration.getTypeAliasRegistry().registerAlias(com.mlh.model.User.class);
        configuration.addMapper(UserDao.class);

        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        return  builder.build(configuration);
}

//    @Test
    public void testCache1() throws  Exception{
//       SqlSessionFactory sqlSessionFactory = buildSqlSessionFactory();
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(new DynamicDataSource());
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.type-aliases-package")));

        SqlSessionFactory sqlSessionFactory =  fb.getObject();

       SqlSession sqlSession = sqlSessionFactory.openSession();
//
        String statement = "com.mlh.dao.useDao.findUserById";
        User user = sqlSession.selectOne(statement,2);
        System.out.println(user);

        user = sqlSession.selectOne(statement);
        System.out.println(user);
        sqlSession.close();

        user = sqlSession.selectOne(statement, 1);
        System.out.println(user);

        user = sqlSession.selectOne(statement, 2);
        System.out.println(user);

        //session.clearCache();
        user = sqlSession.selectOne(statement, 2);
        System.out.println(user);
    }

        @Test
        public void testTwoLevelCache() {
//            String statement = "me.gacl.mapping.userMapper.getUser";
//            //开启两个不同的SqlSession
//            //使用二级缓存时，User类必须实现一个Serializable接口===> User implements Serializable
//            User user = session2.selectOne(statement, 1);
//            session2.commit();//不懂为啥，这个地方一定要提交事务之后二级缓存才会起作用
//            System.out.println("user="+user);
//
//            //由于使用的是两个不同的SqlSession对象，所以即使查询条件相同，一级缓存也不会开启使用
//            user = session2.selectOne(statement, 1);
//            //session2.commit();
//            System.out.println("user2="+user);
        }
}
