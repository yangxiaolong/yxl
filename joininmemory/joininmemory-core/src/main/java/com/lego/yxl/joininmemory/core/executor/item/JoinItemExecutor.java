package com.lego.yxl.joininmemory.core.executor.item;

import java.util.List;

public interface JoinItemExecutor<DATA> {

    void execute(List<DATA> datas);

    default int runOnLevel() {
        return 0;
    }

}
