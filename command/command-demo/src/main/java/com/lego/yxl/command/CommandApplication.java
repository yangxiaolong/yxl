package com.lego.yxl.command;

import com.lego.yxl.command.core.EnableCommandApplicationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * https://mp.weixin.qq.com/s/u9zYxDIx-GP2rTXHF9ttHw
 */
@SpringBootApplication(scanBasePackages = {"com.lego.yxl"})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = "com.lego.yxl.command.command")
@EnableCommandApplicationService(
        basePackages = "com.lego.yxl.command.command"
)
public class CommandApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandApplication.class, args);
    }

}
