package com.lyw.cloud.conformity.mapper;

import com.lyw.cloud.conformity.entity.dto.OauthClientDetailsDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 17:09
 */
@Repository
public interface OAuthDao {

    @Select("select * from oauth_client_details where client_id =#{clientId}")
    OauthClientDetailsDto loadOauthClientDetailsByClientId(String clientId);

}