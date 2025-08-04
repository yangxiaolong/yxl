package com.lego.yxl.core.wide;

public interface WideFactory<MASTER_ID, W extends Wide> {
    W create(MASTER_ID  masterId);
}
