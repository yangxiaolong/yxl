package com.lego.yxl.jpatest.enumschain;

// 基于枚举的处理器管理
public enum MessageHandlers implements MessageHandler {

    TEXT_HANDLER(MessageType.TEXT) {
        @Override
        boolean doHandle(Message message) {
            System.out.println("text");
            return true;
        }
    },
    BIN_HANDLER(MessageType.BIN) {
        @Override
        boolean doHandle(Message message) {
            System.out.println("bin");
            return true;
        }
    },
    XML_HANDLER(MessageType.XML) {
        @Override
        boolean doHandle(Message message) {
            System.out.println("xml");
            return true;
        }
    },
    JSON_HANDLER(MessageType.JSON) {
        @Override
        boolean doHandle(Message message) {
            System.out.println("json");
            return true;
        }
    };

    // 接受的类型
    private final MessageType acceptType;

    MessageHandlers(MessageType acceptType) {
        this.acceptType = acceptType;
    }

    // 抽象接口
    abstract boolean doHandle(Message message);

    // 如果消息体是接受类型，调用doHandle进行业务处理
    @Override
    public boolean handle(Message message) {
        return message.type() == this.acceptType && doHandle(message);
    }

}
