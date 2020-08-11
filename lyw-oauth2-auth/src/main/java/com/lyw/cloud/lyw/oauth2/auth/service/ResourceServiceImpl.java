package com.lyw.cloud.lyw.oauth2.auth.service;

import cn.hutool.core.collection.CollUtil;
import com.lyw.cloud.lyw.oauth2.auth.constant.RedisConstant;
import com.lyw.cloud.lyw.oauth2.auth.dao.PermissionDao;
import com.lyw.cloud.lyw.oauth2.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * @Author: luohx
 * @Description: 资源与角色匹配关系管理业务类
 * @Date: 2020/7/30 11:52
 * @Version: 1.0
 */
@Service
public class ResourceServiceImpl {

    private Map<String, List<String>> resourceRolesMap;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private PermissionDao permissionDao;

    @PostConstruct
    public void initData() {
        resourceRolesMap = new TreeMap<>();
        List<Permission> permissionList = permissionDao.findAll();
        if(!CollUtil.isEmpty(permissionList)){
            for (Permission perm: permissionList) {
                if(!CollUtil.isEmpty(perm.getAuthorities())){
                    List<String> perms = perm.getAuthorities().stream().map(m -> m.getName()).collect(Collectors.toList());
                    resourceRolesMap.put(perm.getUrl(), perms);
                }
            }
        }

        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
