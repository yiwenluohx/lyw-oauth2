package com.lyw.cloud.lyw.oauth2.api.service;

import com.lyw.cloud.lyw.oauth2.api.client.AuthServiceClient;
import com.lyw.cloud.lyw.oauth2.api.dao.UserDao;
import com.lyw.cloud.lyw.oauth2.domain.JWT;
import com.lyw.cloud.lyw.oauth2.domain.User;
import com.lyw.cloud.lyw.oauth2.domain.user.response.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: luohx
 * @Date: 2020/8/7 10:06
 * @Version: 1.0
 */
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthServiceClient authClient;

    @Autowired
    private UserDao userDao;

    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userDao.save(user);
    }

    public UserLoginDTO login(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("用户密码不对");
        }
        JWT jwt = authClient.getToken("password", "client-app", "123456", username, password);
        if (jwt == null) {
            throw new RuntimeException("用户token有问题");
        }
        UserLoginDTO dto = new UserLoginDTO();
        dto.setUser(user);
        dto.setJwt(jwt);

        return dto;
    }

}
