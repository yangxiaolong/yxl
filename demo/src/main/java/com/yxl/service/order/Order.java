package com.yxl.service.order;

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
@AllArgsConstructor
@NoArgsConstructor
public class Order implements WideItemData<WideOrderType, Long> {
    private Long id;
    private Long userId;
    private Long addressId;
    private Long productId;
    private String descr;

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.ORDER;
    }

    @Override
    public Long getKey() {
        return id;
    }
}
