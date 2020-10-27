package com.lyw.cloud.oauth2.server.component;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: luohx
 * @Description: 自定义PasswordEncoder
 * @Date: 2020/10/27 10:26
 */
public class CustomPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String encodeStr = charSequence.toString() + "";
        if (encodeStr.equals(s)) {
            return true;
        }
        return false;
    }
}
