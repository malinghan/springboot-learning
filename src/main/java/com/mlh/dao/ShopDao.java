package com.mlh.dao;

import com.mlh.Annotation.TargetDataSource;
import com.mlh.model.Shop;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/11
 * @description:
 */
@Repository
public interface ShopDao {

    int deleteByPrimaryKey(Integer id);

    int insert(Shop record);

    Shop selectByPrimaryKey(Integer id);

    @TargetDataSource(name = "slave")
    List<Shop> selectAll();

    int updateByPrimaryKey(Shop record);
}