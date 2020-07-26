package com.hry.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hry.springcloud.alibaba.service.PaymentService;
import com.hry.springcloud.entities.CommonResult;
import com.hry.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
public class CircleBreakerController {
    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    HashMap<Integer,Integer> hashMap = new HashMap<Integer, Integer>();

    @Resource
    private PaymentService paymentService;

    @RequestMapping(value = "/consumer/fallback/{id}")
    //@SentinelResource(value = "fallback") //不配置的
    //@SentinelResource(value = "fallback",fallback = "handlerFallback") //fallback只负责业务异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler") //blockHandler只负责sentinel控制台配置违规
    @SentinelResource(value = "fallback",fallback = "handlerFallback",blockHandler = "blockHandler",exceptionsToIgnore = {IllegalArgumentException.class})
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){
        CommonResult<Payment> result = restTemplate.getForObject(SERVICE_URL+"/paymentSQL/"+id,CommonResult.class,id);

        if (id == 1004){
            throw new IllegalArgumentException("IllegalArgumentException , 非法参数异常......");
        }else if (result.getData() == null){
            throw new NullPointerException("NullPointerException , 该ID没有对应记录，空指针异常");
        }
        return result;
    }

    public CommonResult<Payment> handlerFallback(@PathVariable("id") Long id,Throwable e){
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(444,"handlerFallback兜底方法 , 异常"+e.getMessage(),payment);
    }

    public CommonResult<Payment> blockHandler(@PathVariable("id") Long id, BlockException blockException){
        Payment payment = new Payment(id,"null");
        return new CommonResult<>(4444,"blockHandler-sentinel限流，无此流水 , blockException"+blockException.getMessage(),payment);
    }


    @GetMapping(value = "/consumer/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        return paymentService.paymentSQL(id);
    }

}
