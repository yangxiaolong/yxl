package com.lego.yxl.validator.service;

import com.lego.yxl.repository.product.Product;
import com.lego.yxl.repository.stock.Stock;
import com.lego.yxl.repository.user.User;
import lombok.Data;


@Data
public class CreateOrderContext {
    private User user;
    private Product product;
    private Stock stock;
    private Integer count;
}
