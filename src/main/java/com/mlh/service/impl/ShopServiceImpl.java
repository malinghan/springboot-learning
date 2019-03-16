package com.mlh.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mlh.dao.ShopDao;
import com.mlh.model.Shop;
import com.mlh.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/10/11
 * @description:
 */
@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopDao shopDao;//这里会报错，但是并不会影响

    @Override
    public int add(Shop shop) {
        return shopDao.insert(shop);
    }

    /*
    * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
    * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
    * pageNum 开始页数
    * pageSize 每页显示的数据条数
    * */
    @Override
    public PageInfo<Shop> findAllShop(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<Shop> userDomains = shopDao.selectAll();
        PageInfo result = new PageInfo(userDomains);
        return result;
    }
}
