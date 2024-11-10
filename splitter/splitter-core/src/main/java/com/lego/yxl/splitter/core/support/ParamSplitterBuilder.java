package com.lego.yxl.splitter.core.support;

import com.lego.yxl.splitter.core.ParamSplitter;

/**
 */
public interface ParamSplitterBuilder {
    boolean support(Class paramCls);

    ParamSplitter build(Class paramCls);
}
