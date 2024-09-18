package com.lego.yxl.core.factory.item;

import com.lego.yxl.core.executor.item.JoinItemExecutor;

import java.util.List;

public interface JoinItemExecutorFactory {
    <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls);
}
