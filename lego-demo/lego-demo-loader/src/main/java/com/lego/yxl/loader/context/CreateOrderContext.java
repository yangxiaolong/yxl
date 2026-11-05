package com.lego.yxl.loader.context;

import com.lego.yxl.loader.repository.Address;
import com.lego.yxl.loader.repository.Price;
import com.lego.yxl.loader.repository.Product;
import com.lego.yxl.loader.repository.Stock;
import com.lego.yxl.loader.repository.User;

public interface CreateOrderContext {

    User getUser();

    Product getProduct();

    Address getDefAddress();

    Stock getStock();

    Price getPrice();

    CreateOrderCmd getCmd();

}
