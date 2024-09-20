package com.lego.yxl;

public interface CommandForUpdateById<ID> extends CommandForUpdate {
    ID getId();
}
