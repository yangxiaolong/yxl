package com.lego.yxl.loader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/5q6DohyVSS4JyprUibt1Sg
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class,
        scanBasePackages = {"com.lego.yxl.loader", "com.lego.yxl.repository"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoaderApplication.class, args);
    }

}
