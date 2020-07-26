package com.hry.springcloud.alibaba.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.hry.springcloud.entities.CommonResult;
import com.hry.springcloud.entities.Payment;

public class CustomerBlockHandler {
    public static CommonResult handlerException(BlockException exception){
        return new CommonResult(1234567,"按客户自定义全局-----Global-----1");
    }

    public static CommonResult handlerException2(BlockException exception){
        return new CommonResult(7654321,"按客户自定义全局-----Global-----2");
    }
}
