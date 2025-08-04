package com.lego.yxl.singlequery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * https://mp.weixin.qq.com/s/FcdTBwJeW2LiQcohY-wIvg
 */
@SpringBootApplication(scanBasePackages = {"com.lego.yxl.singlequery.starter", "com.lego.yxl.singlequery"})

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.lego.yxl.singlequery.singlequery.jpa")
public class SingleQueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SingleQueryApplication.class, args);
    }

}
