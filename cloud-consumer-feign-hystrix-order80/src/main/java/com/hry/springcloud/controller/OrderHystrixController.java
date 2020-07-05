package com.hry.springcloud.controller;

import com.hry.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "defaultFallbackMethod")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    //@HystrixCommand
    @GetMapping(value = "/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        //int a = 1/0;
        String result = paymentHystrixService.paymentInfo_OK(id);
        return result;
    }

    /**
     * 缺省方法
     * @return
     */
    public String defaultFallbackMethod(){
        return "执行了缺省方法";
    }

    /**
     * 超时访问
     * fallbackMethod:指定方法名：PaymentInfo_TimeOutHandler 来处理
     * value指定时间为2s
     */
    @HystrixCommand(fallbackMethod = "PaymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "2000")
    })
    @GetMapping(value = "/consumer/payment/hystrix/timeout/{id}")
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id) {
        String s = paymentHystrixService.paymentInfo_TimeOut(id);
        return s;
    }

    /**
     * 用于处理fallback
     * @param
     * @return
     */

    public String PaymentInfo_TimeOutHandler(@PathVariable("id") Integer id) {
        String s = "消费者80出错啦！！！！！！！！ 支付系统8001繁忙，请稍后再试";
        return s;
    }


}
