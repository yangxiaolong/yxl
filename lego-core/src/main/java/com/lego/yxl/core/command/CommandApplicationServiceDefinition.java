package com.lego.yxl.core.command;

import com.lego.yxl.core.command.support.AggRoot;
import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 标记一个接口为 CommandService，系统自动生成实现的代理对象
 */
@Indexed
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandApplicationServiceDefinition {
    Class<? extends AggRoot> domainClass();

    Class<? extends CommandRepository> repositoryClass();
}
