package com.lego.yxl.msg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * https://mp.weixin.qq.com/s/zxGB6KNr8QFV57ILZWC_Ig
 * https://mp.weixin.qq.com/s/J_x5HP9jd4CuEsv0JV4Qfw
 * https://mp.weixin.qq.com/s/mrAVSwpufh_TgohhEvnJqQ
 */
@SpringBootApplication(scanBasePackages = {
        "com.lego.yxl.msg",
        "com.lego.yxl.msg.starter"}
)
public class MsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsgApplication.class, args);
    }

}