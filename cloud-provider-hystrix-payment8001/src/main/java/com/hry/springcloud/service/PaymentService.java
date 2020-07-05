package com.hry.springcloud.service;

import org.springframework.web.bind.annotation.PathVariable;

public interface PaymentService {
    public String paymentInfo_OK(Integer id);

    public String paymentInfo_TimeOut(Integer id);

    public String PaymentInfo_TimeOutHandler(Integer id);

    /*----------------服务熔断----------------*/
    public String paymentCricuitBreaker(Integer id);

    public String paymentCircuitBreaker_fallback(Integer id);
}
