package com.hry.springcloud.alibaba.controller;

import com.hry.springcloud.entities.CommonResult;
import com.hry.springcloud.entities.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();

    //模拟dao层  偷懒
    static {
        hashMap.put(1001L,new Payment(1001L,"1001abcd"));
        hashMap.put(1002L,new Payment(1002L,"1002abcd"));
        hashMap.put(1003L,new Payment(1003L,"1003abcd"));
    }

    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult<Payment>(200,"from MySQL , serverPort: "+serverPort,payment);
        return result;
    }
}
