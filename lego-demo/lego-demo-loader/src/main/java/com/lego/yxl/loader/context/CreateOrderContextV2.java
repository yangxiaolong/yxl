package com.lego.yxl.loader.context;

import com.lego.yxl.core.loader.annotation.LazyLoadBy;
import com.lego.yxl.loader.service.Address;
import com.lego.yxl.loader.service.Price;
import com.lego.yxl.loader.service.Product;
import com.lego.yxl.loader.service.Stock;
import com.lego.yxl.loader.service.User;
import lombok.Data;

@Data
public class CreateOrderContextV2 implements CreateOrderContext{

    private CreateOrderCmd cmd;

    @LazyLoadBy("#{@userRepository.getById(cmd.userId)}")
    private User user;

    @LazyLoadBy("#{@productRepository.getById(cmd.productId)}")
    private Product product;

    @LazyLoadBy("#{@addressRepository.getDefaultAddressByUserId(user.id)}")
    private Address defAddress;

    @LazyLoadBy("#{@stockRepository.getByProductId(product.id)}")
    private Stock stock;

    @LazyLoadBy("#{@priceService.getByUserAndProduct(user.id, product.id)}")
    private Price price;
}
