package com.lego.yxl.core.wide;

public interface WideWrapper<W extends Wide> {
    W getTarget();

    default <I> void bindItem(I item){
        updateItem(item);
    }

    <I> boolean isSameWithItem(I item);

    <I> void updateItem(I item);
}
