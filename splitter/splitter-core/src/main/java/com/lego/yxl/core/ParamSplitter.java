package com.lego.yxl.core;

import java.util.List;

/**
 *
 *
 * 参数拆分器，将入参按照一定规则进行拆分
 * @param <P> 入参类型
 */
public interface ParamSplitter<P> {

    /**
     * 将 param 按照 maxSize 进行拆分
     * @param param 原输入参数
     * @param maxSize 拆分后，每个分区的最大元素个数
     * @return
     */
    List<P> split(P param, int maxSize);
}
