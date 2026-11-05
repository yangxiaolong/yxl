package com.lego.yxl.loader.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceRepository {
    public Price getByUserAndProduct(Long user, Long product){
        log.info("==Do Get Price for User {} and Product {}", user, product);
        return new Price();
    }
}
