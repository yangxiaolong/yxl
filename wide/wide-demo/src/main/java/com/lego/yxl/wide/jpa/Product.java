package com.lego.yxl.wide.jpa;

import com.lego.yxl.wide.WideItemData;
import com.lego.yxl.wide.WideOrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ProductS")
@Table(name = "t_product_s")
public class Product implements WideItemData<WideOrderType, Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;

    public boolean isSaleable() {
        return true;
    }

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.PRODUCT;
    }

    @Override
    public Long getKey() {
        return id;
    }
}
