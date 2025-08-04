package com.lego.yxl.feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/sounmvvkCWaAaT2EYvohVA
 */
@SpringBootApplication(scanBasePackages =
        {"com.lego.yxl.core.feign", "com.lego.yxl.feign"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class FeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeignApplication.class, args);
    }

}