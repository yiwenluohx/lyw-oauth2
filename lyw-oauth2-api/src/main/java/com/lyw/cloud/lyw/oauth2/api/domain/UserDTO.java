package com.lyw.cloud.lyw.oauth2.api.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: luohx
 * @Date: 2020/7/30 11:19
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private List<String> roles;
}
