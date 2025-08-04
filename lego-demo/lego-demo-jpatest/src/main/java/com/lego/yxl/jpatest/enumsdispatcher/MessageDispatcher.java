package com.lego.yxl.jpatest.enumsdispatcher;

import java.util.EnumMap;
import java.util.Map;

// 基于EnumMap的消息分发器
public class MessageDispatcher {

    private final Map<MessageType, MessageHandler> dispatcherMap = new EnumMap<>(MessageType.class);

    public MessageDispatcher() {
        dispatcherMap.put(MessageType.LOGIN, message -> System.out.println("Login"));
        dispatcherMap.put(MessageType.ENTER_ROOM, message -> System.out.println("Enter Room"));

        dispatcherMap.put(MessageType.EXIT_ROOM, message -> System.out.println("Exit Room"));
        dispatcherMap.put(MessageType.LOGOUT, message -> System.out.println("Logout"));
    }

    public void dispatch(Message message) {
        MessageHandler handler = this.dispatcherMap.get(message.type());
        if (handler != null) {
            handler.handle(message);
        }
    }

    public static void main(String[] args) {
        MessageDispatcher dispatcher = new MessageDispatcher();
        dispatcher.dispatch(new Message(MessageType.LOGIN, null));
        dispatcher.dispatch(new Message(MessageType.ENTER_ROOM, null));
        dispatcher.dispatch(new Message(MessageType.EXIT_ROOM, null));
        dispatcher.dispatch(new Message(MessageType.LOGOUT, null));
    }
}