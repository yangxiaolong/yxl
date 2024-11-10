package com.lego.yxl.msg.sender;

import com.lego.yxl.msg.core.sender.Message;
import com.lego.yxl.msg.core.sender.ReliableMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TestMessageSenderService {

    @Autowired
    private ReliableMessageSender reliableMessageSender;

    public void testNoTransaction(){
        // 业务逻辑
        Message message = buildMessage();
        this.reliableMessageSender.send(message);
    }

    public void testNoTransactionError(){
        // 业务逻辑
        Message message = buildMessage();
        this.reliableMessageSender.send(message);
        throw new RuntimeException();
    }


    @Transactional
    public void testSuccess(){
        // 业务逻辑
        Message message = buildMessage();
        // 业务逻辑
        this.reliableMessageSender.send(message);
    }

    @Transactional
    public void testError(){
        // 业务逻辑
        Message message = buildMessage();
        // 业务逻辑
        this.reliableMessageSender.send(message);
        throw new RuntimeException();
    }

    private Message buildMessage() {
        Message message = Message.builder()
                .msg("msg")
                .orderly(true)
                .shardingKey("123")
                .topic("test_topic")
                .tag("tag")
                .build();
        return message;
    }
}
