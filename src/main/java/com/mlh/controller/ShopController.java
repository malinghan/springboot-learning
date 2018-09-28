package com.mlh.controller;

import com.mlh.model.Shop;
import com.mlh.service.IShop2Service;
import com.mlh.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/28
 * @description:
 */
@RestController
public class ShopController {

    @Autowired
    private IShopService shopServiceImpl;

    @Autowired
    private IShop2Service shop2ServiceImpl;

    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public List<Shop> selectAllShop(){
        System.out.println("查询第一个数据源");
        return shopServiceImpl.selectAllShop();
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public List<Shop> selectAllShop2(){
        System.out.println("查询第二个数据源");
        return shop2ServiceImpl.selectShopDetail();
    }
}
