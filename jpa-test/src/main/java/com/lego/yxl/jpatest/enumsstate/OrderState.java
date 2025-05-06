package com.lego.yxl.jpatest.enumsstate;

// 基于枚举的状态机实现
public enum OrderState implements IOrderState {

    CREATED {
        // 允许进行cancel操作，并把状态设置为CANCELED
        @Override
        public void cancel(OrderStateContext context) {
            context.setStats(CANCELED);
        }

        // 允许进行confirm操作，并把状态设置为CONFIRMED
        @Override
        public void confirm(OrderStateContext context) {
            context.setStats(CONFIRMED);
        }
    },

    CONFIRMED {
        // 允许进行cancel操作，并把状态设置为CANCELED
        @Override
        public void cancel(OrderStateContext context) {
            context.setStats(CANCELED);
        }

        // 允许进行timeout操作，并把状态设置为OVERTIME
        @Override
        public void timeout(OrderStateContext context) {
            context.setStats(OVERTIME);
        }

        // 允许进行pay操作，并把状态设置为PAID
        @Override
        public void pay(OrderStateContext context) {
            context.setStats(PAID);
        }
    },

    // 最终状态，不允许任何操作
    CANCELED {

    },

    // 最终状态，不允许任何操作
    OVERTIME {

    },

    // 最终状态，不允许任何操作
    PAID {

    };

    @Override
    public void cancel(OrderStateContext context) {
        throw new RuntimeException();
    }

    @Override
    public void confirm(OrderStateContext context) {
        throw new RuntimeException();
    }

    @Override
    public void timeout(OrderStateContext context) {
        throw new RuntimeException();
    }

    @Override
    public void pay(OrderStateContext context) {
        throw new RuntimeException();
    }

}