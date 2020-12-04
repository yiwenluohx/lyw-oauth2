package com.lyw.cloud.conformity.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * @Author: luohx
 * @Description: 描述
 * @Date: 2020/12/4 0004 17:06
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    /** 用户Id**/
    private BigInteger id;

    /** 用户名**/
    private String username;

    /** 用户密码**/
    private String password;

    /** 手机号**/
    private String phone;

    /** 邮件**/
    private String email;

    /** 最后一次时间**/
    private Date lastLoginTime;

}