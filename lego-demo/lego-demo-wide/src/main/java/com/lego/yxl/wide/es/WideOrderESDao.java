package com.lego.yxl.wide.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WideOrderESDao extends ElasticsearchRepository<WideOrder, Long> {
    //public interface WideOrderESDao extends JpaRepository<WideOrder, Long> {
    List<WideOrder> findByProductId(Long productId);

    List<WideOrder> findByAddressId(Long addressId);

    List<WideOrder> findByUserId(Long userId);
}