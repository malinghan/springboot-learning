package com.mlh.mapper.impl;

import java.util.Map;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/17
 * @description:
 */
public class UserSelectProvider {
        private static final String xxxSQL = "user_id,user_name,password,phone";

        public String queryById(Map<String, Object> parameter) {
            org.apache.ibatis.jdbc.SQL sql = new org.apache.ibatis.jdbc.SQL();
            sql.SELECT(xxxSQL).FROM("user").WHERE("user_id = #{userId}");
            return sql.toString();
        }
    }
