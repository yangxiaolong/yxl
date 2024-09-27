package com.lego.yxl.repository.stock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class StockRepository {

    public Stock getByProductId(Long productId){
        log.info("Get Stock By Product Id {}", productId);

        return Stock.builder()
                .id(RandomUtils.nextLong())
                .productId(productId)
                .count(10)
                .build();
    }
}
