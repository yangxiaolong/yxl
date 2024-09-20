package com.lego.yxl.support.merger;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 *  List 结果合并器
 */
public class ListResultMerger
        extends AbstractFixTypeResultMerger<List> {
    @Override
    protected List defaultValue() {
        return Collections.emptyList();
    }

    @Override
    List doMerge(List<List> lists) {

        int size = lists.stream()
                .filter(Objects::nonNull)
                .mapToInt(List::size)
                .sum();
        if (size == 0){
            return defaultValue();
        }

        List result = Lists.newArrayListWithCapacity(size);
        for (List ds : lists){
            if (!CollectionUtils.isEmpty(ds)){
                result.addAll(ds);
            }
        }
        return result;
    }
}
