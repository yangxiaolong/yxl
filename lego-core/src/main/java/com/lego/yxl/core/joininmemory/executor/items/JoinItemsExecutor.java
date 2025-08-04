package com.lego.yxl.core.joininmemory.executor.items;

import java.util.List;

public interface JoinItemsExecutor<DATA> {
    void execute(List<DATA> datas);
}
