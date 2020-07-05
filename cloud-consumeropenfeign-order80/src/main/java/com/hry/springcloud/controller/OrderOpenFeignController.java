package com.hry.springcloud.controller;

import com.hry.springcloud.entities.CommonResult;
import com.hry.springcloud.entities.Payment;
import com.hry.springcloud.service.PaymentOpenFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class OrderOpenFeignController {
    @Resource
    private PaymentOpenFeignService paymentOpenFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        return paymentOpenFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/openfeign/timeout")
    public String paymentOpenFeignTimeout(){
        //openfeign底层是ribbon 客户端一般默认等待1s，而我们之前在支付模块设置的是3s
        return paymentOpenFeignService.paymentOpenFeignTimeout();
    }


}
