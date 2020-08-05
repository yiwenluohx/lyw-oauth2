package com.lyw.cloud.lyw.oauth2.api.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: luohx
 * @Date: 2020/8/5 10:54
 * @Version: 1.0
 */
public class BPwdEncoderUtil {
    private static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encode(String password) {
        return ENCODER.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return ENCODER.matches(rawPassword, encodedPassword);
    }
}
