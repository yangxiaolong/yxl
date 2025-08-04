package com.lego.yxl.loader.service;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Stock {
    private Long id;
    private Long productId;
    private Integer count;
}
