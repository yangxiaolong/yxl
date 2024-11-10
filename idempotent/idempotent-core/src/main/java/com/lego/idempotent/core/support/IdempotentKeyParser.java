package com.lego.idempotent.core.support;

public interface IdempotentKeyParser {
    String parse(String[] names, Object[] param, String el);
}
