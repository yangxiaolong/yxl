package com.lego.yxl.idempotent.core.core.support;

public interface IdempotentKeyParser {
    String parse(String[] names, Object[] param, String el);
}
