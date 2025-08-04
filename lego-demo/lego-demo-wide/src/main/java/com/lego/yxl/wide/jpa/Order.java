package com.lego.yxl.wide.jpa;

import com.lego.yxl.core.wide.WideItemData;
import com.lego.yxl.wide.core.WideOrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "OrderS")
@Table(name = "t_order_s")
public class Order implements WideItemData<WideOrderType, Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
