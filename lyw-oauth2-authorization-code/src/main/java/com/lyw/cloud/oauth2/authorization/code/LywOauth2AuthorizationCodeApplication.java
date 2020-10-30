package com.lyw.cloud.oauth2.authorization.code;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class LywOauth2AuthorizationCodeApplication {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
        String cms = bCrypt.encode("{noop}cms");
        String sc = bCrypt.encode("{noop}secret");
        String mm = bCrypt.encode("123");
        SpringApplication.run(LywOauth2AuthorizationCodeApplication.class, args);
    }

}
