package com.mlh.config;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/29
 * @description:
 */
public enum DatabaseType {

    master("master"), slave("slave");


    DatabaseType(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DatabaseType{" +
                "name='" + name + '\'' +
                '}';
    }
}
