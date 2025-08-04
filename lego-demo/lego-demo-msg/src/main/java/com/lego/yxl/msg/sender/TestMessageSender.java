package com.lego.yxl.msg.sender;

import com.google.common.collect.Lists;
import com.lego.yxl.core.msg.sender.Message;
import com.lego.yxl.core.msg.sender.MessageSender;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Slf4j
public class TestMessageSender implements MessageSender {
    private boolean error = false;
    private List<Message> messages = Lists.newArrayList();

    @Override
    public String send(Message message) {
        log.info("receive message {}", message);
        if (this.error){
            throw new RuntimeException();
        }
        this.messages.add(message);
        return String.valueOf(RandomUtils.nextLong());
    }

    public void clean(){
        this.messages.clear();
    }

    public void markError() {
        this.error = true;
    }

    public void cleanError(){
        this.error = false;
    }
}
