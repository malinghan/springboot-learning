package com.mlh.datasource;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/26
 * @description:
 */
/**
 * 作用：
 * 1、保存一个线程安全的DatabaseType容器
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<DatabaseType> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DatabaseType type){
        contextHolder.set(type);
    }

    public static DatabaseType getDatabaseType(){
        return contextHolder.get();
    }
}