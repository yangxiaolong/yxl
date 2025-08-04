package com.lego.yxl.core.wide.support;

import com.lego.yxl.core.wide.Wide;
import com.lego.yxl.core.wide.WideIndexCompareContext;
import com.lego.yxl.core.wide.WideIndexSingleUpdateContext;
import com.lego.yxl.core.wide.WideItemType;

public abstract class BindFromBasedWide<ID, ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>>
        implements Wide<ID, ITEM_TYPE> {
    @Override
    public boolean isSameWithItem(WideIndexCompareContext<ITEM_TYPE> context) {
        return context.getWideWrapper().isSameWithItem(context.getItemData());
    }

    @Override
    public void updateByItem(WideIndexSingleUpdateContext<ITEM_TYPE> context) {
        context.getWideWrapper().updateItem(context.getItemData());
    }
}
