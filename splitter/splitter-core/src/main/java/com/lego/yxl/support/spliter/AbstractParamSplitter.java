package com.lego.yxl.support.spliter;

import com.lego.yxl.ParamSplitter;

import java.util.Collections;
import java.util.List;

/**
 *
 * 参数拆分器公共父类
 */
abstract class AbstractParamSplitter<P> implements ParamSplitter<P> {

    @Override
    public final List<P> split(P param, int maxSize) {
        if (param == null){
            return defaultValue();
        }
        return doSplit(param, maxSize);
    }

    protected abstract List<P> doSplit(P param, int maxSize);


    protected List<P> defaultValue() {
        return Collections.emptyList();
    }
}
