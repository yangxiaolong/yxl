package com.geekhalo.like;

import com.lego.yxl.core.command.EnableCommandApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {
        "com.geekhalo.like",
        "com.lego.yxl.loader.starter",
        "com.lego.yxl.validator.starter",
        "com.lego.yxl.async.starter"
})
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableCommandApplicationService(basePackages = "com.geekhalo.like.app")
//@EnableQueryApplicationService(basePackages = "com.geekhalo.like.app")
public class LikeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LikeApplication.class, args);
    }
}
