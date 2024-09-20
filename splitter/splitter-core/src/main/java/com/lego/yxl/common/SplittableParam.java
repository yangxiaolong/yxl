package com.lego.yxl.common;

import java.util.List;

public interface SplittableParam<P extends SplittableParam<P>> {
    List<P> split(int maxSize);
}