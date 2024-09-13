package com.yxl.vo;

import java.util.List;

/**
 */
public interface OrderDetailService {
    List<? extends OrderDetailVO> getByUserId(Long userId);
}
