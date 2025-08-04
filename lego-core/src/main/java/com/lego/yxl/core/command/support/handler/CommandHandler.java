package com.lego.yxl.core.command.support.handler;


public interface CommandHandler<CMD, RESULT> {
    RESULT handle(CMD cmd);
}
