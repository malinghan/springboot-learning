package com.mlh.service.impl;

import com.mlh.mapper.ShopMapper;
import com.mlh.model.Shop;
import com.mlh.service.IShop2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/27
 * @description:
 */
@Service
public class Shop2ServiceImpl implements IShop2Service{

    @Autowired
    private ShopMapper shopMapper;

   public List<Shop> selectShopDetail(){
       return shopMapper.selectAll();
   }
}
