package com.lego.yxl.loader.context;

import com.lego.yxl.loader.annotation.*;
import com.lego.yxl.repository.address.Address;
import com.lego.yxl.repository.price.Price;
import com.lego.yxl.repository.product.Product;
import com.lego.yxl.repository.stock.Stock;
import com.lego.yxl.repository.user.User;
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
