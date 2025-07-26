package com.lego.yxl.wide;

import java.util.List;
import java.util.function.Consumer;

public interface WidePatrolService<
        MASTER_DATA_ID, // 主数据 ID
        ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE> // 关联数据类型
        > {

    void index(MASTER_DATA_ID id);

    void index(List<MASTER_DATA_ID> ids);

    <KEY> void updateItem(ITEM_TYPE type, KEY key);

    void setReindexConsumer(Consumer<List<MASTER_DATA_ID>> consumer);
}
