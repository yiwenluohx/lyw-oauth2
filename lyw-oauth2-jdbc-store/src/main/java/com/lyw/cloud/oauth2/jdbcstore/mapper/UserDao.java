package com.lyw.cloud.oauth2.jdbcstore.mapper;

import com.lyw.cloud.oauth2.jdbcstore.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/23 0023 17:44
 */
@Repository
public interface UserDao {

    @Select("select * from user where username=#{username}")
    User findByUsername(@Param("username") String username);

}
