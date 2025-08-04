package com.geekhalo.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {
        "com.geekhalo.tinyurl",
        "com.lego.yxl.loader.starter",
        "com.lego.yxl.validator.starter",
        "com.lego.yxl.async.starter"
})
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class TinyUrlApplication {
    public static void main(String[] args) {
        SpringApplication.run(TinyUrlApplication.class, args);
    }
}
