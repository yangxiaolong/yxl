package com.lego.yxl.support.spliter;

import com.google.common.collect.Lists;
import com.lego.yxl.SmartParamSplitter;

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
