package com.lego.yxl.idempotent.core.core.support;

import com.lego.yxl.idempotent.core.core.IdempotentExecutor;
import org.aopalliance.intercept.MethodInvocation;

public class NllIdempotentExecutor implements IdempotentExecutor {
    private static final NllIdempotentExecutor INSTANCE = new NllIdempotentExecutor();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invocation.proceed();
    }

    public static NllIdempotentExecutor getInstance(){
        return INSTANCE;
    }
}
