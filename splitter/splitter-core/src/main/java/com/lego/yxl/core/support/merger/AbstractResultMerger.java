package com.lego.yxl.core.support.merger;

import com.lego.yxl.core.ResultMerger;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 */
public abstract class AbstractResultMerger<R>
    implements ResultMerger<R> {

    @Override
    public final R merge(List<R> rs) {
        if (CollectionUtils.isEmpty(rs)){
            return defaultValue();
        }

        return doMerge(rs);
    }

    abstract R doMerge(List<R> rs);

    protected R defaultValue(){
        return null;
    }
}
