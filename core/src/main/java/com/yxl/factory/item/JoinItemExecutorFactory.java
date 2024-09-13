package com.yxl.factory.item;

import com.yxl.executor.item.JoinItemExecutor;

import java.util.List;

public interface JoinItemExecutorFactory {
    <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls);
}
