package com.lyw.cloud.lyw.oauth2.api.service;

import com.lyw.cloud.lyw.oauth2.api.dao.UserDao;
import com.lyw.cloud.lyw.oauth2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author: luohx
 * @Date: 2020/8/7 10:06
 * @Version: 1.0
 */
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    public User insertUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return userDao.save(user);
    }

}
