package com.lego.yxl;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;


@Indexed
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandServiceDefinition {
    Class<? extends AggRoot> domainClass();

    Class<? extends CommandRepository> repositoryClass();
}
