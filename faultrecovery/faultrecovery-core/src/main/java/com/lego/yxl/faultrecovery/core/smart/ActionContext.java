package com.lego.yxl.faultrecovery.core.smart;

public class ActionContext {
    private static final ThreadLocal<ActionType> ACTION_TYPE_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(ActionType actionType) {
        ACTION_TYPE_THREAD_LOCAL.set(actionType);
    }

    public static ActionType get() {
        return ACTION_TYPE_THREAD_LOCAL.get();
    }

    public static void clear() {
        ACTION_TYPE_THREAD_LOCAL.remove();
    }
}
