package com.geekhalo.like;

import com.lego.yxl.command.core.EnableCommandApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
@EnableCommandApplicationService(basePackages = "com.geekhalo.like.app")
//@EnableQueryApplicationService(basePackages = "com.geekhalo.like.app")
public class LikeApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(LikeApplication.class, args);

        log.info("Spring Boot Started : {}", application);
    }
}
