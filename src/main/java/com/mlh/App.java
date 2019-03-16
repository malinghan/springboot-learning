package com.mlh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
//dao接口的扫描我们放在配置类中进行
@SpringBootApplication(scanBasePackages = "com.mlh",exclude = {DataSourceAutoConfiguration.class})
public class App{
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class,args);
    }
}
