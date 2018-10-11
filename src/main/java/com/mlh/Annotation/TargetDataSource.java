package com.mlh.Annotation;

import java.lang.annotation.*;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/28
 * @description: 切换DataSource判断的DataSourceAnnotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface TargetDataSource {
    //master、slave
    String name();
}
