package com.lego.yxl.msg.core.sender;


public interface ReliableMessageSender {
    void send(Message message);
}