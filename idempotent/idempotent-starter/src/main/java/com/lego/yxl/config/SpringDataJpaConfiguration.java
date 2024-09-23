package com.lego.yxl.config;

import com.lego.yxl.core.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {
        "com.lego.yxl.core.support.repository"
}, repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class)
public class SpringDataJpaConfiguration {
}
