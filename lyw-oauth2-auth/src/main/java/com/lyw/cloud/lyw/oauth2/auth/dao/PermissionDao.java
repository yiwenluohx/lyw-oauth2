package com.lyw.cloud.lyw.oauth2.auth.dao;

import com.lyw.cloud.lyw.oauth2.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: luohx
 * @Date: 2020/8/11 13:36
 * @Version: 1.0
 */
public interface PermissionDao extends JpaRepository<Permission, Long> {

}
