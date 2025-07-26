package com.lego.yxl.wide;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Objects;

public interface Wide<ID, ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>> {
    ID getId();

    boolean isValidate();

    boolean isSameWithItem(WideIndexCompareContext<ITEM_TYPE> context);

    void updateByItem(WideIndexSingleUpdateContext<ITEM_TYPE> context);

    default void updateByItem(WideIndexBatchUpdateContext<ITEM_TYPE> context) {
        if (CollectionUtils.isNotEmpty(context.getItemDatas())) {
            context.getItemDatas().stream()
                    .filter(Objects::nonNull)
                    .map(item -> new WideIndexSingleUpdateContext<>(context.getType(), item, context.getWideWrapper()))
                    .forEach(this::updateByItem);
        }
    }

    List<WideItemKey> getItemsKeyByType(ITEM_TYPE type);
}
