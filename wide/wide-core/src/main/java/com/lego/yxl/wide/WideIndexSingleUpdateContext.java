package com.lego.yxl.wide;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
@Setter(AccessLevel.PRIVATE)
public class WideIndexSingleUpdateContext<ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>>
        extends WideIndexContext<ITEM_TYPE> {

    private WideItemData<ITEM_TYPE, ?> itemData;
    private WideWrapper wideWrapper;

    public WideIndexSingleUpdateContext(ITEM_TYPE type, WideItemData<ITEM_TYPE,?> itemData, WideWrapper wideWrapper){
        this.setType(type);
        this.setItemData(itemData);
        this.setWideWrapper(wideWrapper);
    }
}
