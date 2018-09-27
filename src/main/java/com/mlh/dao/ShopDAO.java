//package com.mlh.dao;
//
//import com.mlh.datasource.DatabaseContextHolder;
//import com.mlh.datasource.DatabaseType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
///**
// * @author: linghan.ma
// * @DATE: 2018/9/26
// * @description:
// */
//@Repository
//public class ShopDAO {
//
//
//    @Autowired
//    private ShopMapper shopMapper;
//
//    /**
//     * 获取shop
//     */
//    public Shop getShop(int id) {
//        DatabaseContextHolder.setDatabaseType(DatabaseType.mytestdb2);
//        return shopMapper.getShop(id);
//    }
//}
