package com.lego.yxl.command.core;

import lombok.Data;
import lombok.ToString;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * QueryServiceMethod 丢失异常；
 */
@Data
@ToString(callSuper = true)
public class CommandServiceMethodLostException extends RuntimeException{
    private Set<Method> lostMethods;
    public CommandServiceMethodLostException(Set<Method> lostMethods) {
        super();
        this.lostMethods = lostMethods;
    }

    public CommandServiceMethodLostException(String message) {
        super(message);
    }

    public CommandServiceMethodLostException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandServiceMethodLostException(Throwable cause) {
        super(cause);
    }

    protected CommandServiceMethodLostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
