package com.lego.yxl.joininmemory.vo;

public abstract class OrderDetailVO {
    public abstract OrderVO getOrder();

    public abstract UserVO getUser();

    public abstract AddressVO getAddress();

    public abstract ProductVO getProduct();
}
