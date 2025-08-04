package com.lego.yxl.validator;

import com.lego.yxl.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * https://mp.weixin.qq.com/s/LfllLuJjxG5YJoceHLEx7g
 */
@SpringBootApplication(scanBasePackages = {
        "com.lego.yxl.validator.starter",
        "com.lego.yxl.validator"
})

@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {
        "com.lego.yxl.validator.service"
}, repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class)
public class ValidatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatorApplication.class, args);
    }

}