package com.lego.yxl.msg.core.sender.support;

import com.lego.yxl.msg.core.sender.Message;
import com.lego.yxl.msg.core.sender.ReliableMessageSender;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class LocalTableBasedReliableMessageSender implements ReliableMessageSender {
    private final ReliableMessageSendService reliableMessageSendService;


    public LocalTableBasedReliableMessageSender(ReliableMessageSendService reliableMessageSendService) {
        this.reliableMessageSendService = reliableMessageSendService;
    }

    @Override
    public void send(Message message) {
        this.reliableMessageSendService.saveAndSend(message);
    }
}
