package com.geekhalo.tinyurl.config;

import com.lego.yxl.command.core.EnableCommandApplicationService;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCommandApplicationService(
        basePackages = "com.geekhalo.tinyurl.app"
)
public class ApplicationConfiguration {
}
