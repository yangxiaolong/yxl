package com.lego.yxl.idempotent.core.core;

import org.aopalliance.intercept.MethodInvocation;

public interface IdempotentExecutor {
    Object invoke(MethodInvocation invocation) throws Throwable;
}
