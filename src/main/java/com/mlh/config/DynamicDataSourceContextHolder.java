package com.mlh.config;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/26
 * @description:
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 作用：
 * 1、保存一个线程安全的DatabaseType容器
 */
public class DynamicDataSourceContextHolder {
    /*
     * 使用ThreadLocal维护变量，ThreadLocal为每个使用该变量的线程提供独立的变量副本，
     * 所以每一个线程都可以独立地改变自己的副本，而不会影响其它线程所对应的副本。
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(String type){
        contextHolder.set(type);
    }

    public static String getDatabaseType(){
        return contextHolder.get();
    }

    /**
     * @Title: clearDateSoureType
     * @Description: 清空所有的数据源变量
     * @return void
     * @throws
     */
    public static void clearDataSoureType(){
        contextHolder.remove();
    }



}