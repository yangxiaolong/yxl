package com.lego.yxl.support.spring.invoker;

/**
 *
 * 拆分执行器，主要是对方法调用的封装
 */
public interface SplitInvoker {
    Object invoke(Object target, Object[] args);
}
