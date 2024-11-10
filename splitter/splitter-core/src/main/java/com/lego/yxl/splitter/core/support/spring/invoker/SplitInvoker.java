package com.lego.yxl.splitter.core.support.spring.invoker;

/**
 *
 * 拆分执行器，主要是对方法调用的封装
 */
public interface SplitInvoker {
    Object invoke(Object target, Object[] args);
}
