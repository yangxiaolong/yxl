package com.lego.yxl.core.command;

import com.lego.yxl.core.command.support.CommandConfiguration;
import com.lego.yxl.core.command.support.CommandServiceBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({CommandServiceBeanDefinitionRegistrar.class, CommandConfiguration.class})
public @interface EnableCommandService {
    /**
     * 扫描包
     *
     * @return
     */
    String[] basePackages() default {};

    /**
     * 自定义实现Bean后缀
     *
     * @return
     */
    String customImplementationPostfix() default "Impl";
}
