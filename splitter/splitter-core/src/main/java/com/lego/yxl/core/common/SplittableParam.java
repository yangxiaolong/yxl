package com.lego.yxl.core.common;

import java.util.List;

public interface SplittableParam<P extends SplittableParam<P>> {
    List<P> split(int maxSize);
}