package com.lego.yxl.joininmemory.core.executor.items;

import java.util.List;

public interface JoinItemsExecutor<DATA> {
    void execute(List<DATA> datas);
}
