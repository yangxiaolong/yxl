package com.lego.yxl.loader.context;

import com.lego.yxl.loader.annotation.*;
import com.lego.yxl.loader.service.Address;
import com.lego.yxl.loader.service.Price;
import com.lego.yxl.loader.service.Product;
import com.lego.yxl.loader.service.Stock;
import com.lego.yxl.loader.service.User;
import lombok.Data;

@Data
public class CreateOrderContextV1 implements CreateOrderContext {

    private CreateOrderCmd cmd;

    @LazyLoadUserById(userId = "cmd.userId")
    private User user;

    @LazyLoadProduceById(productId = "cmd.productId")
    private Product product;

    @LazyLoadDefaultAddressByUserId(userId = "user.id")
    private Address defAddress;

    @LazyLoadStockByProductId(productId = "product.id")
    private Stock stock;

    @LazyLoadPriceByUserAndProduct(userId = "user.id", productId = "product.id")
    private Price price;

}
