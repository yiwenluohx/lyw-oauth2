package com.lyw.cloud.lyw.oauth2.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.lyw.cloud.lyw.oauth2.domain")
public class LywOauth2AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LywOauth2AuthApplication.class, args);
    }

}
