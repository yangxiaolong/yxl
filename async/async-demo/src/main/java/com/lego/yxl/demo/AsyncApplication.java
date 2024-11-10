package com.lego.yxl.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/DOOB6xnuFRAYTfxJz87Siw
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }

}