package com.lego.yxl.wide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableElasticsearchRepositories(basePackages = "com.lego.yxl.wide.es")
@EnableJpaRepositories(basePackages = {"com.lego.yxl.wide.jpa"})
@SpringBootApplication(scanBasePackages = {"com.lego.yxl.wide", "com.lego.yxl.delay.starter"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class WideApplication {

    public static void main(String[] args) {
        SpringApplication.run(WideApplication.class);
    }

}
