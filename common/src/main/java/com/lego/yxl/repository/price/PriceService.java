package com.lego.yxl.repository.price;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PriceService {
    public Price getByUserAndProduct(Long user, Long product){
        log.info("Get Price for User {} and Product {}", user, product);
        return new Price();
    }
}
