package com.lyw.cloud.lyw.oauth2.api.dao;

import com.lyw.cloud.lyw.oauth2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: luohx
 * @Date: 2020/8/7 9:05
 * @Version: 1.0
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
