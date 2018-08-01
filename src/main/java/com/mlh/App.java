package com.mlh;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        SpringApplication.run(App.class,args);
    }
}
