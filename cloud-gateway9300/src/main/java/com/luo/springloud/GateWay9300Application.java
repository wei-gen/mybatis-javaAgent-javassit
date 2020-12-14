package com.luo.springloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GateWay9300Application {
    public static void main(String[] args) {
        SpringApplication.run(GateWay9300Application.class,args);
    }
}
