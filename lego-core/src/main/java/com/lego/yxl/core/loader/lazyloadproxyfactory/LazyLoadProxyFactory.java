package com.lego.yxl.core.loader.lazyloadproxyfactory;

public interface LazyLoadProxyFactory {
    <T> T createProxyFor(T t);
}
