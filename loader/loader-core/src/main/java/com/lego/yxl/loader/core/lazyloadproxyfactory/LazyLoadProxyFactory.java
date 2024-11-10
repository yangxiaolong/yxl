package com.lego.yxl.loader.core.lazyloadproxyfactory;

public interface LazyLoadProxyFactory {
    <T> T createProxyFor(T t);
}
