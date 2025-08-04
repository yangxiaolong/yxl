package com.lego.yxl.wide.core;

import com.lego.yxl.core.wide.WideItemDataProvider;
import com.lego.yxl.wide.jpa.Product;
import com.lego.yxl.wide.jpa.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProvider implements WideItemDataProvider<WideOrderType, Long, Product> {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> apply(List<Long> key) {
        return productDao.findAllById(key);
    }

    @Override
    public WideOrderType getSupportType() {
        return WideOrderType.PRODUCT;
    }
}
