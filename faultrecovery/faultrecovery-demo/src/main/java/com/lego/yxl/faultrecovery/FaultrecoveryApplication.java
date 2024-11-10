package com.lego.yxl.faultrecovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.retry.annotation.EnableRetry;

/**
 * https://mp.weixin.qq.com/s/2TPTCvaL7e-AKWisNybadw
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableRetry
public class FaultrecoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(FaultrecoveryApplication.class, args);
    }

}
