package com.lego.yxl.core.mybatis;

/**
 * Created by taoli on 2022/8/26.
 * gitee : https://gitee.com/litao851025/lego
 * 编程就像玩 Lego
 */
public interface ExampleConverterFactory {
    ExampleConverter createFor(Class example);
}
