package com.hry.springcloud.alibaba.service.Impl;

import com.hry.springcloud.alibaba.service.PaymentService;
import com.hry.springcloud.entities.CommonResult;
import com.hry.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceImpl implements PaymentService {
    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(55555,"服务降级返回---来自PaymentServiceImpl",new Payment(id,"error"));
    }
}
