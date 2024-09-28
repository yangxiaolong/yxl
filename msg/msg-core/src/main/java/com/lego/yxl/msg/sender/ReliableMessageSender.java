package com.lego.yxl.msg.sender;


public interface ReliableMessageSender {
    void send(Message message);
}