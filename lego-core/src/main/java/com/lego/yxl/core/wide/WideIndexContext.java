package com.lego.yxl.core.wide;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.PROTECTED)
public abstract class WideIndexContext<ITEM_TYPE extends Enum<ITEM_TYPE> & WideItemType<ITEM_TYPE>> {
    private ITEM_TYPE type;
}
