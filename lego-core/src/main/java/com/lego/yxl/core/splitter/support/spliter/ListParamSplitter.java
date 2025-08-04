package com.lego.yxl.core.splitter.support.spliter;

import com.google.common.collect.Lists;
import com.lego.yxl.core.splitter.SmartParamSplitter;

import java.util.List;

/**
 *
 * List 拆分器
 *
 */
public class ListParamSplitter
        extends AbstractFixTypeParamSplitter<List>
        implements SmartParamSplitter<List> {

    @Override
    protected List<List> doSplit(List param, int maxSize) {
        return Lists.partition(param, maxSize);
    }
}
