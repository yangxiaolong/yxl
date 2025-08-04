package com.lego.yxl.core.splitter.support.spliter;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lego.yxl.core.splitter.SmartParamSplitter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * Set 拆分器
 *
 */
public class SetParamSplitter
        extends AbstractFixTypeParamSplitter<Set>
        implements SmartParamSplitter<Set> {
    @Override
    protected List<Set> doSplit(Set param, int maxSize) {
        if (CollectionUtils.isEmpty(param)){
            return defaultValue();
        }

        List<Set> result = Lists.newArrayList();
        Set set = Sets.newHashSetWithExpectedSize(maxSize);
        result.add(set);
        for (Object o : param){
            set.add(o);
            if (set.size() == maxSize){
                set = Sets.newHashSetWithExpectedSize(maxSize);
                result.add(set);
            }
        }
        return result.stream()
                .filter(r -> !CollectionUtils.isEmpty(r))
                .collect(Collectors.toList());
    }
}
