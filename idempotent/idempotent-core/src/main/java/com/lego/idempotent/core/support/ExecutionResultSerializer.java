package com.lego.idempotent.core.support;

public interface ExecutionResultSerializer {
    <T> String serializeResult(T data);

    <T> T deserializeResult(String data, Class<T> cls);

    <T extends Exception> String serializeException(T data);

    <T extends Exception> T deserializeException(String data);
}
