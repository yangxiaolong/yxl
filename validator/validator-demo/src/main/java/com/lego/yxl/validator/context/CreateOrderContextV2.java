package com.lego.yxl.validator.context;

import com.lego.yxl.loader.core.annotation.LazyLoadBy;
import com.lego.yxl.repository.address.Address;
import com.lego.yxl.repository.price.Price;
import com.lego.yxl.repository.product.Product;
import com.lego.yxl.repository.stock.Stock;
import com.lego.yxl.repository.user.User;
import lombok.Data;

@Data
public class CreateOrderContextV2 implements CreateOrderContext {

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
