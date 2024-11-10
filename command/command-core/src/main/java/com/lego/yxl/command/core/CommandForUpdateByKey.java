package com.lego.yxl.command.core;

public interface CommandForUpdateByKey<KEY> extends CommandForUpdate {
    KEY getKey();
}
