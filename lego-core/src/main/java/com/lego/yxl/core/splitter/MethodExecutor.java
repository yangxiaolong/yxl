package com.lego.yxl.core.splitter;

import java.util.List;
import java.util.function.Function;

/**
 *
 * 方法执行器
 */
public interface MethodExecutor {
    /**
     * 执行函数，并返回结果
     * @param function 待执行的函数
     * @param ps 执行函数所需的参数
     * @param <P> 入参
     * @param <R> 返回值
     * @return
     * 所有的执行结果
     */
    <P,R> List<R> execute(Function<P, R> function, List<P> ps);
}
