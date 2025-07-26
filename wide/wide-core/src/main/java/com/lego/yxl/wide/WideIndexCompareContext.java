package com.lego.yxl.wide;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PRIVATE)
public class WideIndexCompareContext<ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>>
        extends WideIndexContext<ITEM_TYPE> {

    private WideItemData<ITEM_TYPE, ?> itemData;

    private WideWrapper wideWrapper;

    public WideIndexCompareContext(ITEM_TYPE type, WideItemData<ITEM_TYPE, ?> itemData, WideWrapper wideWrapper) {
        setType(type);
        setItemData(itemData);
        setWideWrapper(wideWrapper);
    }
}
