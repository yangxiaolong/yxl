package com.yxl.service.product;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static com.yxl.util.TimeUtils.sleepAsMS;

/**
 */
@Repository
@Slf4j
public class ProductRepository {

    public List<Product> getByIds(List<Long> ids){
        sleepAsMS(10);
        return ids.stream()
                .map(id -> createProduct(id))
                .collect(toList());
    }

    public Product getById(Long id){
        sleepAsMS(3);
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
