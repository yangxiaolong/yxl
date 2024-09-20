package com.lego.yxl.support;

import com.lego.yxl.ParamSplitter;

/**
 */
public interface ParamSplitterBuilder {
    boolean support(Class paramCls);

    ParamSplitter build(Class paramCls);
}
