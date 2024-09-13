package com.yxl.core;

import java.util.List;

public interface JoinItemExecutorFactory {
    <DATA> List<JoinItemExecutor<DATA>> createForType(Class<DATA> cls);
}
