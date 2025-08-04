package com.lego.yxl.jpatest.enumsdispatcher;

// 消息体
public record Message(MessageType type, Object data) {
}

// 消息类型
enum MessageType {
    // 登录
    LOGIN,
    // 进入房间
    ENTER_ROOM,
    // 退出房间
    EXIT_ROOM,
    // 登出
    LOGOUT;
}

// 消息处理器
interface MessageHandler {
    void handle(Message message);
}
