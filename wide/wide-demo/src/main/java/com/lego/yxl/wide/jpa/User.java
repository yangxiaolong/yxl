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
@Entity(name = "UserS")
@Table(name = "t_user_s")
public class User implements WideItemData<WideOrderType, Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
