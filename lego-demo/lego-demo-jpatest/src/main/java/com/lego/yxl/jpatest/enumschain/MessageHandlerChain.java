package com.lego.yxl.jpatest.enumschain;

// 消息处理链
public class MessageHandlerChain {
    public boolean handle(Message message) {
        for (MessageHandler handler : MessageHandlers.values()) {
            if (handler.handle(message)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MessageHandlerChain chain = new MessageHandlerChain();
        chain.handle(new Message(MessageType.TEXT, null));
        chain.handle(new Message(MessageType.BIN, null));
        chain.handle(new Message(MessageType.XML, null));
        chain.handle(new Message(MessageType.JSON, null));
    }

}
