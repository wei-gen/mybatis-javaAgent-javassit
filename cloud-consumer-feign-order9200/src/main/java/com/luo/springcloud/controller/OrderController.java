package com.luo.springcloud.controller;

import com.luo.springcloud.entities.CommonResult;
import com.luo.springcloud.entities.Payment;
import com.luo.springcloud.feignService.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    PaymentService paymentService;

    @GetMapping(value = "con/payment/get/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id) {
        return paymentService.getPayment(id);
    }

}
