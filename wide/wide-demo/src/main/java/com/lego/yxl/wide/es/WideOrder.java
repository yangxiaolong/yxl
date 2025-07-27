package com.lego.yxl.wide.es;

import com.lego.yxl.wide.BindFrom;
import com.lego.yxl.wide.WideItemKey;
import com.lego.yxl.wide.WideOrderType;
import com.lego.yxl.wide.jpa.Address;
import com.lego.yxl.wide.jpa.Order;
import com.lego.yxl.wide.jpa.Product;
import com.lego.yxl.wide.jpa.User;
import com.lego.yxl.wide.support.BindFromBasedWide;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
//@Entity
//@Table(name = "t_wide_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "wide_order")
public class WideOrder extends BindFromBasedWide<Long, WideOrderType> {
    @Id
    private Long id;

    @BindFrom(sourceClass = Order.class, field = "userId")
    private Long userId;
    @BindFrom(sourceClass = Order.class, field = "addressId")
    private Long addressId;
    @BindFrom(sourceClass = Order.class, field = "productId")
    private Long productId;
    @BindFrom(sourceClass = Order.class, field = "descr")
    private String orderDescr;

    @BindFrom(sourceClass = User.class, field = "name")
    private String userName;

    @BindFrom(sourceClass = Address.class, field = "detail")
    private String addressDetail;

    @BindFrom(sourceClass = Product.class, field = "name")
    private String productName;
    @BindFrom(sourceClass = Product.class, field = "price")
    private Integer productPrice;

    public WideOrder(Long orderId) {
        setId(orderId);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isValidate() {
        return userId != null && addressId != null && productId != null;
    }

    @Override
    public List<WideItemKey> getItemsKeyByType(WideOrderType wideOrderType) {
        return switch (wideOrderType) {
            case ORDER -> Collections.singletonList(new WideItemKey(wideOrderType, getId()));
            case USER -> Collections.singletonList(new WideItemKey(wideOrderType, getUserId()));
            case ADDRESS -> Collections.singletonList(new WideItemKey(wideOrderType, getAddressId()));
            case PRODUCT -> Collections.singletonList(new WideItemKey(wideOrderType, getProductId()));
        };

    }
}
