package com.lyw.cloud.conformity.mapper;

import com.lyw.cloud.conformity.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 17:08
 */
@Repository
public interface UserDao {

    @Select("select * from user where username=#{username}")
    User findByUsername(@Param("username") String username);

}