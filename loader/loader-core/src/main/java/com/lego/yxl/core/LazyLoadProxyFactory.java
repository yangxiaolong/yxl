package com.lego.yxl.core;

public interface LazyLoadProxyFactory {
    <T> T createProxyFor(T t);
}
