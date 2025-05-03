package com.lego.yxl.jpatest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auther yangxiaolong
 * @create 2025/5/1
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // 注册 Hibernate 模块（处理代理对象）
        mapper.registerModule(new Hibernate6Module());
        return mapper;
    }
}