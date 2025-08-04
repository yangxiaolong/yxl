package com.lego.yxl.core.msg.sender;


public interface ReliableMessageSender {
    void send(Message message);
}