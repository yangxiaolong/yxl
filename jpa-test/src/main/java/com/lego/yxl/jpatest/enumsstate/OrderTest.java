package com.lego.yxl.jpatest.enumsstate;

public class OrderTest {

    public static void main(String[] args) {
        // 创建订单（自动初始化为CREATED状态）
        Order order = new Order();

        try {
            // 1. 确认订单（CREATED -> CONFIRMED）
            order.confirm();
            System.out.println("当前状态：" + order.getState());

            // 2. 支付订单（CONFIRMED -> PAID）
            order.pay();
            System.out.println("当前状态：" + order.getState());

            // 3. 尝试取消已支付订单（应抛出异常）
            order.cancel();
        } catch (RuntimeException e) {
            System.out.println("操作失败：");
        }
    }

}
