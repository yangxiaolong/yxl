package com.geekhalo.tinyurl.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
//        "com.geekhalo.tinyurl.infra.repository",
//        "com.geekhalo.tinyurl.infra.generator.db.gen",
        "com.geekhalo.tinyurl"
})
public class JpaConfiguration {
}
