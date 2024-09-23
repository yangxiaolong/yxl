package com.lego.yxl.core.support;

public interface IdempotentKeyParser {
    String parse(String[] names, Object[] param, String el);
}
