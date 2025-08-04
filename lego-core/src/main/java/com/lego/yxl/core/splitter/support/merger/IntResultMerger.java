package com.lego.yxl.core.splitter.support.merger;

import com.lego.yxl.core.splitter.SmartResultMerger;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 */
public class IntResultMerger
        extends AbstractResultMerger<Integer>
        implements SmartResultMerger<Integer> {

    @Override
    public boolean support(Class<Integer> resultType) {
        return Integer.class == resultType || Integer.TYPE == resultType;
    }

    @Override
    Integer doMerge(List<Integer> integers) {
        if (CollectionUtils.isEmpty(integers)){
            return 0;
        }
        return integers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }
}
