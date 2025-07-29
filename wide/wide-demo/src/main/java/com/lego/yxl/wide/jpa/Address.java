package com.lego.yxl.wide.jpa;

import com.lego.yxl.wide.WideItemData;
import com.lego.yxl.wide.core.WideOrderType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "AddressS")
@Table(name = "t_address_s")
public class Address implements WideItemData<WideOrderType, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String detail;

    @Override
    public WideOrderType getItemType() {
        return WideOrderType.ADDRESS;
    }

    @Override
    public Long getKey() {
        return id;
    }

}
