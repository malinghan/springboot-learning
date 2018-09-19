//package com.mlh.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @author: linghan.ma
// * @DATE: 2018/9/17
// * @description:  RED数据源
// */
//@Configuration
//@MapperScan(basePackages = RdsDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "rdsSqlSessionFactory")
//public class RdsDataSourceConfig {
//    static final String PACKAGE = "com.aliyun.xxx.repository.mybatis.rds";
//
//    @Value("${xxx_mysql_url}")
//    private String dbUrl;
//    @Value("${xxx_mysql_username}")
//    private String dbUser;
//    @Value("${xxx_mysql_password}")
//    private String dbPassword;
//
//    @Bean(name = "rdsDataSource")
//    @Primary
//    public DataSource rdsDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl(dbUrl);
//        dataSource.setUsername(dbUser);
//        dataSource.setPassword(dbPassword);
//        return dataSource;
//    }
//
//    @Bean(name = "rdsTransactionManager")
//    @Primary
//    public DataSourceTransactionManager rdsTransactionManager() {
//        return new DataSourceTransactionManager(rdsDataSource());
//    }
//
//    @Bean(name = "rdsSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory rdsSqlSessionFactory(@Qualifier("rdsDataSource") DataSource rdsDataSource) throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(rdsDataSource);
//        return sessionFactory.getObject();
//    }
//}
