package com.lego.yxl.core.command;

public interface CommandForUpdateByKey<KEY> extends CommandForUpdate {
    KEY getKey();
}
