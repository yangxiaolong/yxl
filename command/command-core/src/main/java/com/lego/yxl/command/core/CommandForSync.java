package com.lego.yxl.command.core;

public interface CommandForSync<KEY> extends Command{
    KEY getKey();
}
