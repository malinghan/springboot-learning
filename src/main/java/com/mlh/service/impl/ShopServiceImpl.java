package com.mlh.service.impl;

import com.mlh.mapper.ShopMapper;
import com.mlh.model.Shop;
import com.mlh.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/27
 * @description:
 */
@Service
public class ShopServiceImpl implements IShopService {

    @Autowired
    private ShopMapper shopMapper;

    public List<Shop> selectAllShop(){
        return shopMapper.selectAll();
    }
}
