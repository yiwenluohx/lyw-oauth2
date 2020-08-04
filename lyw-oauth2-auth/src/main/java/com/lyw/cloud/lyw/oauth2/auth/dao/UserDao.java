package com.lyw.cloud.lyw.oauth2.auth.dao;

import com.lyw.cloud.lyw.oauth2.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: luohx
 * @Date: 2020/8/4 18:02
 * @Version: 1.0
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
