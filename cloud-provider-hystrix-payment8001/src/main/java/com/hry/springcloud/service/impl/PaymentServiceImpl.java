package com.hry.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.hry.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {
    /**
     * 正常访问
     * @param id
     * @return
     */
    @Override
    public String paymentInfo_OK(Integer id) {
        String s = "线程池："+Thread.currentThread().getName()+ "  paymentInfo_OK,id："+id+"\t";
        return s;
    }

    /**
     * 超时访问
     * fallbackMethod:指定方法名：PaymentInfo_TimeOutHandler 来处理
     * value指定时间为2s
     */
    @HystrixCommand(fallbackMethod = "PaymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    @Override
    public String paymentInfo_TimeOut(Integer id) {
        int time = 3;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String s = "线程池："+Thread.currentThread().getName()+ "  paymentInfo_TimeOut,id："+id+"\t"+"耗时"+time+"s";
        return s;
    }

    /**
     * 用于处理fallback
     * @param id
     * @return
     */
    @Override
    public String PaymentInfo_TimeOutHandler(Integer id) {
        String s = "线程池："+Thread.currentThread().getName()+ "  TimeOutHandler,id："+id+"\t"+"o(╥﹏╥)o";
        return s;
    }

    /*----------------服务熔断----------------*/

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),   //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  //请求次数（请求容量阈值）
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),    //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60") //失败率达到多少后跳闸
    })
    @Override
    public String paymentCricuitBreaker(Integer id) {
        if (id < 0){
            throw new RuntimeException("***id 不能为负数");
        }
        String simpleUUID = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+simpleUUID;
    }

    @Override
    public String paymentCircuitBreaker_fallback(Integer id) {
        return "id 不能负数，请稍后再试，id :" +id;
    }


}
