package com.lego.yxl.core.splitter.support.executor;

import com.lego.yxl.core.splitter.MethodExecutor;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * 顺序执行器，依次执行
 */
public class SerialMethodExecutor
        extends AbstractMethodExecutor
        implements MethodExecutor {

    @Override
    protected <R, P> List<R> doExecute(Function<P, R> function, List<P> ps) {
        return ps.stream()
                .map(function)
                .collect(Collectors.toList());
    }
}
