package com.lego.yxl.core.lazyloadproxyfactory;

public interface LazyLoadProxyFactory {
    <T> T createProxyFor(T t);
}
