package com.luo.springcloud;

import com.luo.ribbon.MyRibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="CLOUD-PAYMENT-SERVICE",configuration = MyRibbonConfig.class)
public class OrderApplication9002 {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication9002.class);
    }
}
