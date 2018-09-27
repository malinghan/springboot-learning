package com.mlh.mapper;

import com.mlh.model.Shop;
import java.util.List;

public interface ShopMapper {
    int insert(Shop record);

    List<Shop> selectAll();
}