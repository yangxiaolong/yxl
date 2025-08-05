package com.lego.yxl.delay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * https://mp.weixin.qq.com/s/sGgr3hYGPTr8P7hGJtreEg
 * https://mp.weixin.qq.com/s/fJWwFAkzAJXW2SDj_itG0Q
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages =
        {"com.lego.yxl.delay.starter", "com.lego.yxl.delay"})
public class DelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(DelayApplication.class, args);
    }

}