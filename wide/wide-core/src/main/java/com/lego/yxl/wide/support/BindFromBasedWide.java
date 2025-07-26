package com.lego.yxl.wide.support;

import com.lego.yxl.wide.Wide;
import com.lego.yxl.wide.WideIndexCompareContext;
import com.lego.yxl.wide.WideIndexSingleUpdateContext;
import com.lego.yxl.wide.WideItemType;

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
