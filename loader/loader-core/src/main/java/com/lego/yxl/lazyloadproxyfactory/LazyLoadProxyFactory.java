package com.lego.yxl.lazyloadproxyfactory;

public interface LazyLoadProxyFactory {
    <T> T createProxyFor(T t);
}
