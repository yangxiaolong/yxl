package com.lego.yxl.core.command;

import com.lego.yxl.core.command.support.AggRoot;
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
