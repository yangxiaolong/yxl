package com.lego.yxl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * https://mp.weixin.qq.com/s/FcdTBwJeW2LiQcohY-wIvg
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.lego.yxl.singlequery.jpa")
public class SingleQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleQueryApplication.class, args);
    }

}
