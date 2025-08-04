package com.geekhalo.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {
        "com.geekhalo.feed",
        "com.lego.yxl.validator.starter",
        "com.lego.yxl.loader.starter"
})
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class FeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedApplication.class, args);
    }

}
