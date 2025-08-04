package com.lego.yxl.joininmemory.vo;

import com.lego.yxl.core.joininmemory.annotation.JoinInMemeoryExecutorType;
import com.lego.yxl.core.joininmemory.annotation.JoinInMemoryConfig;
import com.lego.yxl.joininmemory.annotations.JoinAddressVOOnId;
import com.lego.yxl.joininmemory.annotations.JoinProductVOOnId;
import com.lego.yxl.joininmemory.annotations.JoinUserVOOnId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JoinInMemoryConfig(executorType = JoinInMemeoryExecutorType.PARALLEL)
public class OrderDetailVOV6 extends OrderDetailVO {

    private final OrderVO order;

    @JoinUserVOOnId(keyFromSourceData = "#{order.userId}")
    private UserVO user;

    @JoinAddressVOOnId(keyFromSourceData = "#{order.addressId}")
    private AddressVO address;

    @JoinProductVOOnId(keyFromSourceData = "#{order.productId}")
    private ProductVO product;

}
