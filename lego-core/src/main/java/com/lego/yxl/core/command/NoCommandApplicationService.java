package com.lego.yxl.core.command;

import java.lang.annotation.*;

/**
 * 标记该 Bean 非 CommandService，不会生成 proxy 对象
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface NoCommandApplicationService {
}
