package com.mlh.service.impl;

import com.mlh.mapper.ShopMapper;
import com.mlh.model.Shop;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/27
 * @description:
 */
public class Shop2ServiceImpl {

    @Autowired
    private ShopMapper shopMapper;

   public List<Shop> selectShopDetail(){
       return shopMapper.selectAll();
   }
}
