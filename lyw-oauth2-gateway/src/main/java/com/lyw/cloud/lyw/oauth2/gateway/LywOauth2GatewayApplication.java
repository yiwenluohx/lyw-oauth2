package com.lyw.cloud.lyw.oauth2.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class LywOauth2GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(LywOauth2GatewayApplication.class, args);
    }

}
