package com.yxl.vo;

import com.yxl.service.product.Product;
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
