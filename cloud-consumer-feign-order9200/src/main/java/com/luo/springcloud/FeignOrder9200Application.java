package com.luo.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeignOrder9200Application {

    public static void main(String[] args) {
        SpringApplication.run(FeignOrder9200Application.class,args);
    }
}
