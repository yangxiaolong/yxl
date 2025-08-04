package com.lego.yxl.validator.repository;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {
    private Long id;
    private Long productId;
    private Integer count;
}
