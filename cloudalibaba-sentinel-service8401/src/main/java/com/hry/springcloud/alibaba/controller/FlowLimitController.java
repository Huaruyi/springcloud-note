package com.hry.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA(){
        /*try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "testA";
    }

    @GetMapping(value = "/testB")
    public String testB(){
        return "testB";
    }

    @GetMapping(value = "/testC")
    public String testC(){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "testC";
    }

    @GetMapping(value = "/testD")
    public String testD(){

        int a = 1/0;
        return "testD";
    }

    @GetMapping(value = "/testE")
    public String testE(){

        int a = 1/0;
        return "testE";
    }

    @GetMapping(value = "/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "deal_testHotKey") //value任意，比如abc，唯一即可
    public String testHotKey(@RequestParam(value = "p1",required = false)String p1,
                             @RequestParam(value = "p2",required = false)String p2){

        return "testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException exception){

        //sentinel 系统默认为Blocked by sentinel(flow limiting)
        return "########################deal_testHotKey";
    }
}
