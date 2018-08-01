package com.mlh.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }
}
