package com.lyw.cloud.lyw.oauth2.auth.service;

import cn.hutool.core.collection.CollUtil;
import com.lyw.cloud.lyw.oauth2.auth.constant.MessageConstant;
import com.lyw.cloud.lyw.oauth2.auth.dao.UserDao;
import com.lyw.cloud.lyw.oauth2.auth.domain.SecurityUser;
import com.lyw.cloud.lyw.oauth2.auth.domain.UserDTO;
import com.lyw.cloud.lyw.oauth2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: luohx
 * @Description: 用户管理业务类
 * @Date: 2020/7/30 11:54
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> userList = userRepository.findAll();
        if (CollUtil.isEmpty(userList)) {
            throw new UsernameNotFoundException(MessageConstant.ACCOUNT_NULL);
        }
        userList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(userList)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        User user = userList.get(0);
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setStatus(user.isEnabled() ? 1 : 0);
        if (CollUtil.isEmpty(user.getAuthorities())) {
            throw new UsernameNotFoundException(MessageConstant.PERMISSION_DENIED);
        }
        dto.setRoles(user.getAuthorities().stream().map(m -> ((GrantedAuthority) m).getAuthority()).collect(Collectors.toList()));

        SecurityUser securityUser = new SecurityUser(dto);
        if (!securityUser.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return securityUser;
    }

}
