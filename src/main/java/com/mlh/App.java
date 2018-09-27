package com.mlh;

import com.mlh.common.AbstractApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
@SpringBootApplication(scanBasePackages = "com.mlh",exclude = {DataSourceAutoConfiguration.class})
public class App extends AbstractApplication {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        SpringApplication.run(App.class,args);
    }
}
