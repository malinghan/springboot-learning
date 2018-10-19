package com.mlh.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/11
 * @description:
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.mlh.dao")
public class DataSourceConfig {
    private static Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Autowired
    private Environment env;  // (1)

    @Autowired
    private DataSourceProperties properties;  // (2)

    @Value("${spring.datasource.druid.filters}")   // (3)
    private String filters;

    @Value("${spring.datasource.druid.initial-size}")
    private Integer initialSize;

    @Value("${spring.datasource.druid.min-idle}")
    private Integer minIdle;

    @Value("${spring.datasource.druid.max-active}")
    private Integer maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private Integer maxWait;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private Long timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.druid.min-evictable-idle-time-millis}")
    private Long minEvictableIdleTimeMillis;

    @Value("${spring.datasource.druid.validation-query}")
    private String validationQuery;

    @Value("${spring.datasource.druid.test-while-idle}")
    private Boolean testWhileIdle;

    @Value("${spring.datasource.druid.test-on-borrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.druid.test-on-return}")
    private boolean testOnReturn;

    @Value("${spring.datasource.druid.pool-prepared-statements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.druid.max-pool-prepared-statement-per-connection-size}")
    private Integer maxPoolPreparedStatementPerConnectionSize;

    /**
     * 通过Spring JDBC 快速创建 DataSource
     * @return
     */
    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.master")  // (4)
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }


    /**
     * 手动创建DruidDataSource,通过DataSourceProperties 读取配置
     * @return
     * @throws SQLException
     */
    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource() throws SQLException {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setFilters(filters);
        dataSource.setUrl(properties.getUrl());
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        return dataSource;
    }


    /**
     *  构造多数据源连接池
     *  Master 数据源连接池采用 HikariDataSource
     *  Slave  数据源连接池采用 DruidDataSource
     * @param master
     * @param slave
     * @return
     */
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("masterDataSource") DataSource master,
                                        @Qualifier("slaveDataSource") DataSource slave) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DatabaseType.master, master);
        targetDataSources.put(DatabaseType.slave, slave);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(master);
        dataSource.afterPropertiesSet();
//        String read = env.getProperty("spring.datasource.read");
//        dataSource.setMethodType(DatabaseType.slave, read);
//
//        String write = env.getProperty("spring.datasource.write");
//        dataSource.setMethodType(DatabaseType.master, write);

        return dataSource;
    }


//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
//        scannerConfigurer.setBasePackage("com.mlh.dao");
//        Properties props = new Properties();
//        props.setProperty("mappers", "tk.mybatis.mapper.common.Mapper");
//        props.setProperty("IDENTITY", "MYSQL");
//        props.setProperty("notEmpty", "true");
//        scannerConfigurer.setProperties(props);
//        return scannerConfigurer;
//    }



    /**
     * mapper路径
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource,
                                               @Qualifier("slaveDataSource") DataSource slaveDataSource) throws Exception {
        SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
        fb.setDataSource(this.dataSource(masterDataSource, slaveDataSource));
        fb.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        fb.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));

        //分页插件设置
        PageInterceptor pageHelper = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        //添加分页插件
        fb.setPlugins(new Interceptor[]{pageHelper});

        return fb.getObject();
    }

    /**
     * 事务管理
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

}
