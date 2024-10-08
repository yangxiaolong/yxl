package com.lego.yxl.core;

import org.aopalliance.intercept.MethodInvocation;

public interface IdempotentExecutor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
