package com.lego.yxl.factory.item;

import com.lego.yxl.executor.item.JoinItemExecutor;

import java.util.List;

public interface JoinItemExecutorFactory {
    <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls);
}
