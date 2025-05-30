package com.geekhalo.like.infra;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;

@Configuration
@EnableJpaRepositories(
        repositoryFactoryBeanClass = JpaRepositoryFactoryBean.class
//        repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class
)
public class JpaConfiguration {
}