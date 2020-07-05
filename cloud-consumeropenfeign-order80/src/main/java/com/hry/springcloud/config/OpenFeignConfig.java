package com.hry.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfig {

    /**
     * 注意导入的是feign.Logger
     */
    @Bean
    Logger.Level OpenFeignLoggerLevel(){
        return Logger.Level.FULL;
    }
}
