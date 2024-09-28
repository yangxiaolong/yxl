package com.lego.yxl.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/zxGB6KNr8QFV57ILZWC_Ig
 */
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
    }

}