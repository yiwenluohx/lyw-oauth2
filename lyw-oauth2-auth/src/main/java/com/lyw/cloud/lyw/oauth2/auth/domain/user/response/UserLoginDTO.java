package com.lyw.cloud.lyw.oauth2.auth.domain.user.response;

import com.lyw.cloud.lyw.oauth2.auth.domain.JWT;
import com.lyw.cloud.lyw.oauth2.auth.domain.User;
import lombok.Data;

/**
 * @Author: luohx
 * @Date: 2020/8/4 17:58
 * @Version: 1.0
 */
@Data
public class UserLoginDTO {
    private User user;
    private JWT jwt;
}
