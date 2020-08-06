package com.lyw.cloud.lyw.oauth2.domain.user.response;

import com.lyw.cloud.lyw.oauth2.domain.JWT;
import com.lyw.cloud.lyw.oauth2.domain.User;
import lombok.Data;

/**
 * @Author: luohx
 * @Date: 2020/8/5 10:52
 * @Version: 1.0
 */
@Data
public class UserLoginDTO {
    private User user;
    private JWT jwt;
}
