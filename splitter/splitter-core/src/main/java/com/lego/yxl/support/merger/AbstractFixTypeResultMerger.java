package com.lego.yxl.support.merger;

import com.google.common.reflect.TypeToken;
import com.lego.yxl.SmartResultMerger;

/**
 *
 * 从泛型中获取类型，用于进行 support
 */
abstract class AbstractFixTypeResultMerger<R>
        extends AbstractResultMerger<R>
        implements SmartResultMerger<R> {
    private final Class supportType;

    public AbstractFixTypeResultMerger() {
        TypeToken<R> typeToken = new TypeToken<R>(getClass()) {};
        this.supportType = (Class) typeToken.getRawType();
    }

    @Override
    public final boolean support(Class<R> resultType) {
        if (resultType == null){
            return false;
        }
        return this.supportType.isAssignableFrom(resultType);
    }
}
