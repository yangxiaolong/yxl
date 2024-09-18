package com.lego.yxl.joininmemory.service.product;

import com.lego.yxl.joininmemory.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 */
@Repository
@Slf4j
public class ProductRepository {

    public List<Product> getByIds(List<Long> ids){
        TimeUtils.sleepAsMS(10);
        return ids.stream()
                .map(this::createProduct)
                .collect(toList());
    }

    public Product getById(Long id){
        TimeUtils.sleepAsMS(3);
        log.info("Get Product By Id {}", id);
        return createProduct(id);
    }

    private Product createProduct(Long id) {
        return Product.builder()
                .id(id)
                .name("商品-" + id)
                .price(id.intValue() / 100)
                .build();
    }
}
