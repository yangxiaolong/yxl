package com.lego.yxl.jpatest.enumsstate;

// 状态操作接口，管理所有支持的动作
public interface IOrderState {

    void cancel(OrderStateContext context);

    void confirm(OrderStateContext context);

    void timeout(OrderStateContext context);

    void pay(OrderStateContext context);

}