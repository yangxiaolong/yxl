package com.geekhalo.feed.config;

import com.lego.yxl.core.command.EnableCommandApplicationService;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCommandApplicationService(basePackages = "com.geekhalo.feed.app")
//@EnableQueryApplicationService(basePackages = "com.geekhalo.feed.app")
public class ApplicationServiceConfiguration {
}
