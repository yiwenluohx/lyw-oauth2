package com.lyw.cloud.oauth2.server.mapper;

import com.lyw.cloud.oauth2.server.entity.User;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Param;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/27 0027 10:58
 */
public interface UserMapper {

    @Select("select * from sys_user where username=#{username}")
    User findByUsername(@Param("username") String username);

    @Select("select * from sys_user where username=#{username} and password=#{password}")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

}
