package com.lego.yxl.jpatest.enumschain;

// 消息类型
public enum MessageType {
    TEXT, BIN, XML, JSON;
}

// 定义的消息体
record Message(MessageType type, Object object) {

    @Override
    public MessageType type() {
        return type;
    }
}

// 消息处理器
interface MessageHandler {
    boolean handle(Message message);
}
