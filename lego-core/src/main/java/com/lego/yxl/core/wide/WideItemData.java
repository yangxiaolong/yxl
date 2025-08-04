package com.lego.yxl.core.wide;

public interface WideItemData<T extends Enum<T> & WideItemType<T>, KEY> {

    T getItemType();

    KEY getKey();

}
