package com.yxl.service.product;

import com.yxl.wide.WideItemData;
import com.yxl.wide.WideOrderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product implements WideItemData<WideOrderType, Long> {
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
