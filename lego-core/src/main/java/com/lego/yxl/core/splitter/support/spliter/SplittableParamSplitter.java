package com.lego.yxl.core.splitter.support.spliter;

import com.lego.yxl.core.splitter.ParamSplitter;
import com.lego.yxl.core.splitter.common.SplittableParam;

import java.util.List;

/**
 * <p>
 * SplittableParam 拆分器
 */
public class SplittableParamSplitter<P extends SplittableParam<P>>
        extends AbstractFixTypeParamSplitter<P>
        implements ParamSplitter<P> {

    @Override
    protected List<P> doSplit(P param, int maxSize) {
        return param.split(maxSize);
    }

}
