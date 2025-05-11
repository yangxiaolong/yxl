package com.lego.yxl.splitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/uXTFvb-r1_qY00mnnYSZ4Q
 */
@SpringBootApplication
public class SplitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SplitterApplication.class, args);
    }

}
