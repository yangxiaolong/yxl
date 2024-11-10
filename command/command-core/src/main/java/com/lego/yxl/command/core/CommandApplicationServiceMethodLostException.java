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
public class CommandApplicationServiceMethodLostException extends RuntimeException{
    private Set<Method> lostMethods;
    public CommandApplicationServiceMethodLostException(Set<Method> lostMethods) {
        super();
        this.lostMethods = lostMethods;
    }

    public CommandApplicationServiceMethodLostException(String message) {
        super(message);
    }

    public CommandApplicationServiceMethodLostException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandApplicationServiceMethodLostException(Throwable cause) {
        super(cause);
    }

    protected CommandApplicationServiceMethodLostException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
