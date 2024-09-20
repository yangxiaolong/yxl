package com.lego.yxl.vo.v6;

import com.lego.yxl.annotation.JoinInMemeoryExecutorType;
import com.lego.yxl.annotation.JoinInMemoryConfig;
import com.lego.yxl.vo.*;
import lombok.Data;

/**
 */
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
