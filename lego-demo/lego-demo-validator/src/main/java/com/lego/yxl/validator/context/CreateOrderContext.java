package com.lego.yxl.validator.context;

import com.lego.yxl.validator.repository.*;

public interface CreateOrderContext {

    User getUser();

    Product getProduct();

    Address getDefAddress();

    Stock getStock();

    Price getPrice();

    CreateOrderCmd getCmd();

}
