package com.lego.yxl.jpatest.enumsstate;

// 订单实际实现
public class Order {

    private OrderState state = OrderState.CREATED;

    public OrderState getState() {
        return state;
    }

    private void setStats(OrderState state) {
        this.state = state;
    }

    // 将请求转发给状态机
    public void cancel() {
        this.state.cancel(new StateContext());
    }

    // 将请求转发给状态机
    public void confirm() {
        this.state.confirm(new StateContext());
    }

    // 将请求转发给状态机
    public void timeout() {
        this.state.timeout(new StateContext());
    }

    // 将请求转发给状态机
    public void pay() {
        this.state.pay(new StateContext());
    }

    // 内部类，实现OrderStateContext，回写Order的状态
    class StateContext implements OrderStateContext {

        @Override
        public void setStats(OrderState state) {
            Order.this.setStats(state);
        }
    }

}

