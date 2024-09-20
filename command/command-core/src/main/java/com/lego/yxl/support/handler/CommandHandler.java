package com.lego.yxl.support.handler;


public interface CommandHandler<CMD, RESULT> {
    RESULT handle(CMD cmd);
}
