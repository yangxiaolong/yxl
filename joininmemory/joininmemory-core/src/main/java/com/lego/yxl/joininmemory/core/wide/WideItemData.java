package com.lego.yxl.joininmemory.core.wide;


public interface WideItemData<
        T extends Enum<T> & WideItemType<T>,
        KEY> {
    T getItemType();

    KEY getKey();
}
