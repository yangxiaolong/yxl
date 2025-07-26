package com.lego.yxl.wide;

public interface WideWrapperFactory<W extends Wide> {
    WideWrapper<W> createForWide(W wide);
}
