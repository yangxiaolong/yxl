package com.yxl.wide;


/**
 */
public enum WideOrderType implements WideItemType<WideOrderType> {
    ORDER, // 订单主数据
    USER, // 用户数据
    ADDRESS, // 用户地址数据
    PRODUCT // 购买商品数据
}
