package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosConfig9050Application {
    public static void main(String[] args) {
        SpringApplication.run(NacosConfig9050Application.class, args);
    }

}
