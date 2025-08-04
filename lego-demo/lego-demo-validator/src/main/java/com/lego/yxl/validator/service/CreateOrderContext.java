package com.lego.yxl.validator.service;

import com.lego.yxl.validator.repository.*;
import lombok.Data;

@Data
public class CreateOrderContext {
    private User user;
    private Product product;
    private Stock stock;
    private Integer count;
}
