package com.lego.yxl.loader.context;

import com.lego.yxl.repository.address.Address;
import com.lego.yxl.repository.price.Price;
import com.lego.yxl.repository.product.Product;
import com.lego.yxl.repository.stock.Stock;
import com.lego.yxl.repository.user.User;

/**
 */
public interface CreateOrderContext {

    User getUser();

    Product getProduct();

    Address getDefAddress();

    Stock getStock();

    Price getPrice();

    CreateOrderCmd getCmd();

}
