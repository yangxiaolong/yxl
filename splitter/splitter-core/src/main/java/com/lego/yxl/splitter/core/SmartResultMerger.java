package com.lego.yxl.splitter.core;

/**
 *
 * 智能结果合并器
 */
public interface SmartResultMerger<R> extends ResultMerger<R>{
    /**
     * 是否能支持特定结果的合并
     * @param resultType 结果类型
     * @return
     */
    boolean support(Class<R> resultType);
}
