package com.lego.yxl.validator.service;

import com.lego.yxl.core.validator.ValidateException;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "validate_order")
@Data
public class ValidateableOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付金额
     */
    private Integer payPrice;

    /**
     * 售价
     */
    private Integer sellPrice;

    /**
     * 购买数量
     */
    private Integer amount;

    /**
     * 折扣价
     */
    private Integer discountPrice;

    /**
     * 手工改价
     */
    private Integer manualPrice;

    @PrePersist
    @PreUpdate
    void checkPrice(){
        int realPayPrice = sellPrice * amount - discountPrice - manualPrice;

        if (realPayPrice != payPrice){
            throw new ValidateException("order", "570", "金额计算错误");
        }
    }
}
