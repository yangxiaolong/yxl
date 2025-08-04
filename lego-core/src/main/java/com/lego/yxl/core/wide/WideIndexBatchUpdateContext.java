package com.lego.yxl.core.wide;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@Setter(AccessLevel.PRIVATE)
public class WideIndexBatchUpdateContext<ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>>
        extends WideIndexContext<ITEM_TYPE> {

    private List<WideItemData<ITEM_TYPE, ?>> itemDatas;

    private WideWrapper wideWrapper;

    public WideIndexBatchUpdateContext(ITEM_TYPE type, List<WideItemData<ITEM_TYPE, ?>> itemDatas, WideWrapper wideWrapper) {
        setType(type);
        setItemDatas(itemDatas);
        setWideWrapper(wideWrapper);
    }
}
