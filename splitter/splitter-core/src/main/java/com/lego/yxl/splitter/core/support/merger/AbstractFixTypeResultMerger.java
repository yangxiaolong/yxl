package com.lego.yxl.splitter.core.support.merger;

import com.google.common.reflect.TypeToken;
import com.lego.yxl.splitter.core.SmartResultMerger;

/**
 *
 * 从泛型中获取类型，用于进行 support
 */
abstract class AbstractFixTypeResultMerger<R>
        extends AbstractResultMerger<R>
        implements SmartResultMerger<R> {
    private final Class supportType;

    public AbstractFixTypeResultMerger() {
        TypeToken<R> typeToken = new TypeToken<>(getClass()) {};
        this.supportType = typeToken.getRawType();
    }

    @Override
    public final boolean support(Class<R> resultType) {
        if (resultType == null){
            return false;
        }
        return this.supportType.isAssignableFrom(resultType);
    }
}
