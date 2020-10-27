package com.lyw.cloud.oauth2.server.service;

import com.lyw.cloud.oauth2.server.entity.User;
import com.lyw.cloud.oauth2.server.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: luohx
 * @Description:
 * @Date: 2020/10/27 10:29
 */
@Slf4j
@Service("userService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserMapper userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            log.info("登录用户[{}]没注册!",username);
            throw new UsernameNotFoundException("登录用户["+username + "]没注册!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
    }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
