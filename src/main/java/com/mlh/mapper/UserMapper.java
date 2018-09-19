package com.mlh.mapper;

import com.mlh.mapper.impl.UserSelectProvider;
import com.mlh.model.UserPO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/17
 * @description:
 */
@Mapper
public interface UserMapper {
    @SelectProvider(type = UserSelectProvider.class, method = "queryById")
    @Results(value =
            {
            @Result(property="userId",column="user_id"),
            @Result(property="userName",column="user_name"),
            @Result(property="password",column="password"),
            @Result(property="phone",column="phone")
            })
    List<UserPO> queryById(@Param("userId") String userId);
}
