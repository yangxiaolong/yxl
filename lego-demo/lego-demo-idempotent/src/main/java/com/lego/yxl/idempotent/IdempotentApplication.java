package com.lego.yxl.idempotent;

import com.lego.yxl.core.singlequery.jpa.support.JpaBasedQueryObjectRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * https://mp.weixin.qq.com/s/SeV8JvoHLHduB9w-jiInAQ
 */
@SpringBootApplication(scanBasePackages = {
        "com.lego.yxl.idempotent.starter",
        "com.lego.yxl.singlequery.starter",
        "com.lego.yxl.idempotent"
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {
        "com.lego.yxl.core.idempotent.core.support.repository"
}, repositoryFactoryBeanClass = JpaBasedQueryObjectRepositoryFactoryBean.class)
@EntityScan("com.lego.yxl.core.idempotent.core.support.repository")
public class IdempotentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdempotentApplication.class, args);
    }

}