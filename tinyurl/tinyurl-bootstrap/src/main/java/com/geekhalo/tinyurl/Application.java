package com.geekhalo.tinyurl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true, proxyTargetClass = true)
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);

        log.info("Spring Boot Started : {}", application);
    }
}
