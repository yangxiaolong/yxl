package com.lego.yxl.joininmemory.vo;

import java.util.List;

/**
 */
public interface OrderDetailService {
    List<? extends OrderDetailVO> getByUserId(Long userId);
}
