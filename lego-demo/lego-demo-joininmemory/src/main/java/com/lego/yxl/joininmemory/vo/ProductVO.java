package com.lego.yxl.joininmemory.vo;

import com.lego.yxl.joininmemory.repository.Product;
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
