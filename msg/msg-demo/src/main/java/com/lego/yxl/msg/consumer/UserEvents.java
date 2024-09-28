package com.lego.yxl.msg.consumer;

import lombok.Data;


public interface UserEvents {

    interface UserEvent{
        Long getUserId();
    }

    @Data
    class UserCreatedEvent implements UserEvent{
        private Long userId;
        private String userName;
    }

    @Data
    class UserEnableEvent implements UserEvent{
        private Long userId;
        private String userName;
    }

    @Data
    class UserDisableEvent implements UserEvent{
        private Long userId;
        private String userName;
    }

    @Data
    class UserDeletedEvent implements UserEvent{
        private Long userId;
        private String userName;
    }
}
