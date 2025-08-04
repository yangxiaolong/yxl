package com.lego.yxl.core.splitter.support;

import com.lego.yxl.core.splitter.ParamSplitter;

/**
 */
public interface ParamSplitterBuilder {
    boolean support(Class paramCls);

    ParamSplitter build(Class paramCls);
}
