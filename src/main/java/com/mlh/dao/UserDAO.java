package com.mlh.dao;

import com.mlh.model.UserPO;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author: linghan.ma
 * @DATE: 2018/9/17
 * @description:
 */
@Repository
public interface UserDAO {

    int insert(UserPO record);

    List<UserPO> selectUsers();
}
