package com.lego.yxl.core.support;

import com.lego.yxl.core.ParamSplitter;

/**
 */
public interface ParamSplitterBuilder {
    boolean support(Class paramCls);

    ParamSplitter build(Class paramCls);
}
