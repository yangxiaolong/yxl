package com.lego.yxl.command.core.support.handler;


public interface CommandHandler<CMD, RESULT> {
    RESULT handle(CMD cmd);
}
