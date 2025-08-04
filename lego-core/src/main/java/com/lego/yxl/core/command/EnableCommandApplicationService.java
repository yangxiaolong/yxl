package com.lego.yxl.core.command;

import com.lego.yxl.core.command.support.CommandApplicationServiceBeanDefinitionRegistrar;
import com.lego.yxl.core.command.support.CommandConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CommandApplicationServiceBeanDefinitionRegistrar.class, CommandConfiguration.class})
public @interface EnableCommandApplicationService {
    /**
     * 扫描包
     * @return
     */
    String[] basePackages() default {};

    /**
     * 自定义实现Bean后缀
     * @return
     */
    String customImplementationPostfix() default "Impl";
}
