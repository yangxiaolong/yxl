package com.lego.yxl;

import com.lego.yxl.core.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {
        "com.lego.yxl.core.support.repository"
}, repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class)
public class IdempotentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdempotentApplication.class, args);
    }

}