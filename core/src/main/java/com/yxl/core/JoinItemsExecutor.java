package com.yxl.core;

import java.util.List;

public interface JoinItemsExecutor<DATA> {
    void execute(List<DATA> datas);
}
