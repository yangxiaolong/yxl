package com.lego.yxl.core.wide;

public interface WideWrapperFactory<W extends Wide> {
    WideWrapper<W> createForWide(W wide);
}
