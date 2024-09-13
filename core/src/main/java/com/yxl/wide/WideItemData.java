package com.yxl.wide;


public interface WideItemData<
        T extends Enum<T> & WideItemType<T>,
        KEY> {
    T getItemType();

    KEY getKey();
}
