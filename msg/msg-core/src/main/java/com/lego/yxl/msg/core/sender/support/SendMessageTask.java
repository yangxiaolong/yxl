package com.lego.yxl.msg.core.sender.support;

import com.lego.yxl.msg.core.sender.MessageSender;
import com.lego.yxl.msg.core.sender.Message;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SendMessageTask implements Runnable{
    private final LocalMessageRepository localMessageRepository;
    private final MessageSender messageSender;
    @Getter
    private final LocalMessage localMessage;

    public SendMessageTask(LocalMessageRepository localMessageRepository,
                           MessageSender messageSender,
                           LocalMessage localMessage) {
        this.localMessageRepository = localMessageRepository;
        this.messageSender = messageSender;
        this.localMessage = localMessage;
    }

    @Override
    public void run() {
        log.debug("begin to run send task for {}", this.localMessage);
        Message message = this.localMessage.toMessage();
        try {
            String msgId = this.messageSender.send(message);
            this.localMessage.sendSuccess(msgId);
            log.info("success to send to mq, message is {}", message);
        }catch (Exception e){
            this.localMessage.sendError();
            log.error("failed to send message {}", message, e);
        }
        this.localMessageRepository.update(localMessage);
        log.debug("success to run send task for {}", this.localMessage);
    }
}
