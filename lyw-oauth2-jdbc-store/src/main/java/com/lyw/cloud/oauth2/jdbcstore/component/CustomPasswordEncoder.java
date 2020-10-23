package com.lyw.cloud.oauth2.jdbcstore.component;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: luohx
 * @Description: 自定义PasswordEncoder
 * @Date: 2020/10/23 19:29
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        //不加密
        String encodeStr = charSequence.toString() + "";
        if (encodeStr.equals(s)) {
            return true;
        }
        return false;
    }

    /**
     * PasswordEncoder中方法： encode():把参数按照特定的解析规则进行解析。
     *
     * matches()验证从存储中获取的编码密码与编码后提交的原始密
     * 码是否匹配。如果密码匹配，则返回 true；如果不匹配，则返回 false。
     * 第一个参数表示需要被解析的密码。第二个参数表示存储的密码
     *
     * upgradeEncoding()：如果解析的密码能够再次进行解析且达到更
     * 安全的结果则返回 true，否则返回 false。默认返回 false
     *
     * 内置了很多解析器
     * BCryptPasswordEncoder 是 Spring Security 官方推荐的密码解析器，平时多使用这个解析器。
     * 是对 bcrypt 强散列方法的具体实现。是基于 Hash 算法实现的单向加密。可以通过 strength 控制加密强度
     *
     *
     */
}
