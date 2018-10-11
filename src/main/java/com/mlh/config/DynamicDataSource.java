package com.mlh.config;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/26
 * @description:
 */

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态数据源（需要继承AbstractRoutingDataSource）
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        String type  = DynamicDataSourceContextHolder.getDatabaseType();
        if(StringUtils.isEmpty(type)){
            type = DatabaseType.master.getName();
        }
        logger.info("====================dataSource ==========" + type);
        return type;
    }

}
