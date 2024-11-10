package com.lego.yxl.joininmemory.core.factory.item;

import com.lego.yxl.joininmemory.core.executor.item.JoinItemExecutor;

import java.util.List;

public interface JoinItemExecutorFactory {
    <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls);
}
