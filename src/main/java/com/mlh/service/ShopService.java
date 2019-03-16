package com.mlh.service;

import com.github.pagehelper.PageInfo;
import com.mlh.model.Shop;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/11
 * @description:
 */
public interface ShopService {
    int add(Shop shop);

    PageInfo<Shop> findAllShop(int pageNum, int pageSize);
}
