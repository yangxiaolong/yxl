package com.geekhalo.tinyurl.infra.generator;

import com.geekhalo.tinyurl.domain.generator.NumberGenerator;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public abstract class AbstractBatchNumberGenerator implements NumberGenerator {
    private List<Long> tmp = Lists.newArrayList();

    @Override
    public Long nextNumber() {
        synchronized (tmp){
            if (CollectionUtils.isEmpty(tmp)){
                List<Long> numbers = batchLoad();
                tmp.addAll(numbers);
            }
            return tmp.remove(0);
        }
    }

    protected abstract List<Long> batchLoad();
}
