package com.lego.yxl.delay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelayApplication.class, args);
    }

}