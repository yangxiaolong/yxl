package com.lego.yxl.vo;

import com.lego.yxl.repository.product.Product;
import lombok.Value;

/**
 */
@Value
public class ProductVO {
    private Long id;
    private String name;
    private Integer price;

    public static ProductVO apply(Product product){
        if (product == null){
            return null;
        }
        return new ProductVO(product.getId(), product.getName(), product.getPrice());
    }
}
