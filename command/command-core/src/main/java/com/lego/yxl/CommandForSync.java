package com.lego.yxl;

public interface CommandForSync<KEY> extends Command{
    KEY getKey();
}
