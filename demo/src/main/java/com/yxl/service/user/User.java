package com.yxl.service.user;

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
public class User implements WideItemData<WideOrderType, Long> {
    private Long id;
    private String name;

    public boolean isEnable() {
        return true;
    }

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.USER;
    }

    @Override
    public Long getKey() {
        return id;
    }
}