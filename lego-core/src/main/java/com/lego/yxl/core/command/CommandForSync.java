package com.lego.yxl.core.command;

public interface CommandForSync<KEY> extends Command{
    KEY getKey();
}
