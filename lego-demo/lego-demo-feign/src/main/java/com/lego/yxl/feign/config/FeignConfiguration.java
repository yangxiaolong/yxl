package com.lego.yxl.feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@EnableFeignClients(basePackages = "com.lego.yxl.feign.feign")
@Configuration
public class FeignConfiguration {
}
