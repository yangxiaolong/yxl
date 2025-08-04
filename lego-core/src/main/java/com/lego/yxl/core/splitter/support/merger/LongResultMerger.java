package com.lego.yxl.core.splitter.support.merger;

import com.lego.yxl.core.splitter.SmartResultMerger;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 */
public class LongResultMerger
        extends AbstractResultMerger<Long>
        implements SmartResultMerger<Long> {
    @Override
    Long doMerge(List<Long> longs) {
        if (CollectionUtils.isEmpty(longs)){
            return 0L;
        }
        return longs.stream()
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public boolean support(Class<Long> resultType) {
        return Long.class == resultType || Long.TYPE == resultType;
    }
}
