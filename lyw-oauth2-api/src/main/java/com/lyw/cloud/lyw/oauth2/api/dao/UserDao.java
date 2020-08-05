package com.lyw.cloud.lyw.oauth2.api.dao;

import com.lyw.cloud.lyw.oauth2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: luohx
 * @Date: 2020/8/5 10:40
 * @Version: 1.0
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
