package com.yxl.vo.v6;

import com.yxl.annotation.JoinInMemeoryExecutorType;
import com.yxl.annotation.JoinInMemoryConfig;
import com.yxl.vo.*;
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
