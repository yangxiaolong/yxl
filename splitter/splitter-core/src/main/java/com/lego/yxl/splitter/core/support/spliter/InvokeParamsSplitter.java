package com.lego.yxl.splitter.core.support.spliter;

import com.lego.yxl.splitter.core.ParamSplitter;

import java.util.List;

/**
 * 通用参数拆分器
 */
public class InvokeParamsSplitter extends AbstractFixTypeParamSplitter<InvokeParams> {
    private final ParamSplitter paramSplitter;

    public InvokeParamsSplitter(ParamSplitter paramSplitter) {
        this.paramSplitter = paramSplitter;
    }

    @Override
    protected List<InvokeParams> doSplit(InvokeParams param, int maxSize) {
        return param.split(this.paramSplitter, maxSize);
    }
}
