package com.lego.yxl.core.splitter.support.spring.invoker;

import com.lego.yxl.core.splitter.SplitService;
import lombok.Getter;

import java.lang.reflect.Method;

/**
 *
 */
@Getter
abstract class AbstractSplitInvoker implements SplitInvoker {
    private final SplitService splitService;
    private final Method method;
    private final int sizePrePartition;

    protected AbstractSplitInvoker(SplitService splitService, Method method, int sizePrePartition) {
        this.splitService = splitService;
        this.method = method;
        this.sizePrePartition = sizePrePartition;
    }

}
