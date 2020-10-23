package com.lyw.cloud.oauth2.jdbcstore.mapper;

import com.lyw.cloud.oauth2.jdbcstore.entity.dto.OauthClientDetailsDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/10/23 0023 17:44
 */
@Repository
public interface OAuthDao {

    @Select("select * from oauth_client_details where client_id =#{clientId}")
    OauthClientDetailsDto loadOauthClientDetailsByClientId(String clientId);

}
