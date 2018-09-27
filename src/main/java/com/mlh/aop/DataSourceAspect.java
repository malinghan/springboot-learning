package com.mlh.aop;

import com.mlh.dao.ShopDAO;
import com.mlh.datasource.DatabaseContextHolder;
import com.mlh.datasource.DatabaseType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/26
 * @description:
 */
@Component
@Aspect
public class DataSourceAspect {

    @Before("execution(* com.mlh.dao.*.*(..))")
    public void setDataSourceKey(JoinPoint point){
        //连接点所属的类实例是ShopDao
        //还可以实现其他的需求
        //避免事务冲突
        if(point.getTarget() instanceof ShopDAO){
            DatabaseContextHolder.setDatabaseType(DatabaseType.mytestdb2);
        }
    }
}
