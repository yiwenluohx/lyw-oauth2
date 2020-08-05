package com.lyw.cloud.lyw.oauth2.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LywOauth2EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LywOauth2EurekaApplication.class, args);
    }

}
