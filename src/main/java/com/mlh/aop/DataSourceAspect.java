package com.mlh.aop;

import com.mlh.datasource.DatabaseContextHolder;
import com.mlh.datasource.DatabaseType;
import com.mlh.service.IShopService;
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
    //可以改成基于注解的数据源切换

    @Before("execution(* com.mlh.service.*.*(..))")
    public void setDataSourceKey(JoinPoint point){
        //连接点所属的类实例是 IShopService
        //还可以实现其他的需求
        //需要注意避免事务冲突
        if(point.getTarget() instanceof IShopService){
            DatabaseContextHolder.setDatabaseType(DatabaseType.mytestdb2);
        }
    }
}
