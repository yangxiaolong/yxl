package com.lego.yxl.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;

/**
 * https://mp.weixin.qq.com/s/DOOB6xnuFRAYTfxJz87Siw
 */
@SpringBootApplication(scanBasePackages =
        {"com.lego.yxl.async.starter", "com.lego.yxl.async"})
public class AsyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsyncApplication.class, args);
    }

}