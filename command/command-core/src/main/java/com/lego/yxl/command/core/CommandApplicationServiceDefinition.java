package com.lego.yxl.command.core;

import com.lego.yxl.agg.AggRoot;
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
