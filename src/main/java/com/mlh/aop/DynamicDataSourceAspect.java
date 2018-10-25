package com.mlh.aop;

import com.mlh.Annotation.TargetDataSource;
import com.mlh.config.DynamicDataSourceContextHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/26
 * @description: 动态数据源通知
 * @see:  https://www.cnblogs.com/foreveravalon/p/8653832.html
 */
@Component
@Order(-1) // 保证在 @Transactional之前执行
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DynamicDataSourceAspect {

    private static final Logger logger = LogManager.getLogger(DynamicDataSourceAspect.class);

    //改变数据源
    @Before("@annotation(targetDataSource)")
    public void changeDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String dbid = targetDataSource.name();
        logger.debug("使用数据源：" + dbid+ joinPoint.getSignature());
        DynamicDataSourceContextHolder.setDatabaseType(dbid);
    }

    /**
     * 销毁数据源  在所有的方法执行执行完毕后
     * @param joinPoint
     * @param targetDataSource
     */
    @After("@annotation(targetDataSource)")
    public void clearDataSource(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        logger.debug("清除数据源 " + targetDataSource.name() + " !",joinPoint.getSignature());
        DynamicDataSourceContextHolder.clearDataSoureType();
    }
}
