package com.lego.yxl.loader.context;

import com.lego.yxl.loader.service.Address;
import com.lego.yxl.loader.service.Price;
import com.lego.yxl.loader.service.Product;
import com.lego.yxl.loader.service.Stock;
import com.lego.yxl.loader.service.User;

public interface CreateOrderContext {

    User getUser();

    Product getProduct();

    Address getDefAddress();

    Stock getStock();

    Price getPrice();

    CreateOrderCmd getCmd();

}
