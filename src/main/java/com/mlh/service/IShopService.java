package com.mlh.service;

import com.mlh.model.Shop;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/27
 * @description:
 */
@Service
public interface IShopService {
    List<Shop> selectAllShop();
}
