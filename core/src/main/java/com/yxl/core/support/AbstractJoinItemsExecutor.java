package com.yxl.core.support;

import com.google.common.base.Preconditions;
import com.yxl.core.JoinItemExecutor;
import com.yxl.core.JoinItemsExecutor;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;

/**
 */
abstract class AbstractJoinItemsExecutor<DATA> implements JoinItemsExecutor<DATA> {

    @Getter(AccessLevel.PROTECTED)
    private final Class<DATA> dataCls;
    @Getter(AccessLevel.PROTECTED)
    private final List<JoinItemExecutor<DATA>> joinItemExecutors;

    public AbstractJoinItemsExecutor(Class<DATA> dataCls,
                                     List<JoinItemExecutor<DATA>> joinItemExecutors) {
        Preconditions.checkArgument(dataCls != null);
        Preconditions.checkArgument(joinItemExecutors != null);

        this.dataCls = dataCls;
        this.joinItemExecutors = joinItemExecutors;
        this.joinItemExecutors.sort(Comparator.comparingInt(JoinItemExecutor::runOnLevel));
    }

}
