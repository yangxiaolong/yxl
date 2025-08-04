package com.lego.yxl.core.splitter.support.spring.invoker;

import com.lego.yxl.core.splitter.SplitService;
import com.lego.yxl.core.splitter.support.spliter.InvokeParams;

import java.lang.reflect.Method;

/**
 * 多参数 拆分执行器，主要用于对多个入参的方法进行封装
 */
public class MultipleParamSplitInvoker
        extends AbstractSplitInvoker
        implements SplitInvoker {
    private final int splitParamIndex;

    public MultipleParamSplitInvoker(SplitService splitService,
                                     Method method,
                                     int splitParamIndex,
                                     int sizePrePartition) {
        super(splitService, method, sizePrePartition);
        this.splitParamIndex = splitParamIndex;
    }

    @Override
    public Object invoke(Object target, Object[] params) {
        // 对调用参数进行封装
        InvokeParams invokeParams = InvokeParams.builder()
                .splitParamIndex(this.splitParamIndex)
                .parameters(params)
                .build();

        return this.getSplitService().split(param -> invokeMethod(target, (InvokeParams) param),
                invokeParams,
                getSizePrePartition());
    }

    private Object invokeMethod(Object target, InvokeParams param) {
        try {
            return getMethod().invoke(target, param.getParameters());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
