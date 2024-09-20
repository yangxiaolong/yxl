package com.lego.yxl;

public interface CommandForUpdateByKey<KEY> extends CommandForUpdate {
    KEY getKey();
}
