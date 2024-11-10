package com.lego.yxl.delay.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/sGgr3hYGPTr8P7hGJtreEg
 * https://mp.weixin.qq.com/s/fJWwFAkzAJXW2SDj_itG0Q
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class DelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelayApplication.class, args);
    }

}