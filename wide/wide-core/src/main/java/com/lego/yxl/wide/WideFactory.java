package com.lego.yxl.wide;

public interface WideFactory<MASTER_ID, W extends Wide> {
    W create(MASTER_ID  masterId);
}
