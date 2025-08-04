package com.lego.yxl.enums;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther yangxiaolong
 * @create 2025/5/27
 */
@SpringBootApplication(scanBasePackages = {"com.lego.yxl.core.enums","com.lego.yxl.enums"})

public class EnumsApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnumsApplication.class);
    }

}
