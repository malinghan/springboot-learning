package com.mlh.controller;

import com.github.pagehelper.PageInfo;
import com.mlh.model.Shop;
import com.mlh.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/8/1
 * @description:
 */
@RestController
public class HelloController {

    @Autowired
    private ShopService shopService;

    @RequestMapping("hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping(value = "/shop/insert",method = RequestMethod.POST)
    public String insertShop(Shop shop){
        shopService.add(shop);
        return "ok";
    }

    @RequestMapping(value = "/shop/findAll",method = RequestMethod.GET)
    public PageInfo<Shop> findAllShop(){
        return shopService.findAllShop(1,10);
    }


}
