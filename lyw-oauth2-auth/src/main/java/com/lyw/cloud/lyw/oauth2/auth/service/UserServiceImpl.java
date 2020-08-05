package com.lyw.cloud.lyw.oauth2.auth.service;

import com.lyw.cloud.lyw.oauth2.auth.constant.MessageConstant;
import com.lyw.cloud.lyw.oauth2.auth.dao.UserDao;
import com.lyw.cloud.lyw.oauth2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @Author: luohx
 * @Description: 用户管理业务类
 * @Date: 2020/7/30 11:54
 * @Version: 1.0
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    /*
    * 此处换成从库中读取
//    private List<UserDTO> userList;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void initData() {
//        String password = passwordEncoder.encode("123456");
//        userList = new ArrayList<>();
//        userList.add(new UserDTO(1L,"macro", password,1, CollUtil.toList("ADMIN")));
//        userList.add(new UserDTO(2L,"andy", password,1, CollUtil.toList("TEST")));
//    }
* */

    @Autowired
    private UserDao userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //换成从数据库中读取数据
        /*List<UserDTO> findUserList = userList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isEmpty(findUserList)) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }*/
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        //SecurityUser securityUser = new SecurityUser(findUserList.get(0));
        if (!user.isEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        } else if (!user.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }
        return user;
    }

}
