package com.lego.yxl.joininmemory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/TggSrm3C7Fvf-_1q4K7FUQ
 * https://mp.weixin.qq.com/s/YfLoJoGprG8nuFeA6FamwA
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class JoinInMemoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JoinInMemoryApplication.class, args);
    }

}
