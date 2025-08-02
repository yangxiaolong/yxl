package com.geekhalo.feed;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(scanBasePackages = {"com.geekhalo.feed",
        "com.lego.yxl.validator.starter", "com.lego.yxl.command.core", "com.lego.yxl.loader.starter"})
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class FeedApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(FeedApplication.class, args);

        log.info("Spring Boot Started : {}", application);
    }

}
