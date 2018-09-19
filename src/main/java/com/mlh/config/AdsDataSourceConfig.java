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
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @author: linghan.ma
// * @DATE: 2018/9/17
// * @description:
// */
//@Configuration
//@MapperScan(basePackages = AdsDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "adsSqlSessionFactory")
//public class AdsDataSourceConfig {
//    static final String PACKAGE = "com.aliyun.xxx.repository.mybatis.ads";
//
//    @Value("${xxx_ads_url}")
//    private String dbUrl;
//    @Value("${xxx_ads_username}")
//    private String dbUser;
//    @Value("${xxx_ads_password}")
//    private String dbPassword;
//
//    @Bean(name = "adsDataSource")
//    public DataSource adsDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl(dbUrl);
//        dataSource.setUsername(dbUser);
//        dataSource.setPassword(dbPassword);
//        return dataSource;
//    }
//
//    @Bean(name = "adsTransactionManager")
//    public DataSourceTransactionManager adsTransactionManager(@Qualifier("adsDataSource") DataSource adsDataSource) {
//        return new DataSourceTransactionManager(adsDataSource);
//    }
//
//    @Bean(name = "adsSqlSessionFactory")
//    public SqlSessionFactory adsSqlSessionFactory(@Qualifier("adsDataSource") DataSource adsDataSource) throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(adsDataSource);
//        return sessionFactory.getObject();
//    }
//}
