package com.lego.yxl.msg.controller;

import com.lego.yxl.msg.consumer.RocketBasedUserMessageConsumer;
import com.lego.yxl.msg.consumer.UserMessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/msg/rocketmq")
public class RocketBasedUserMessageConsumerTestController extends UserMessageConsumerSupport {

    @Autowired
    private RocketBasedUserMessageConsumer userMessageConsumer;

    @Override
    protected String getTopic() {
        return "consumer-test-topic-1";
    }

    @Override
    protected UserMessageConsumer getUserMessageConsumer() {
        return userMessageConsumer;
    }

    @GetMapping("/userEvents")
    public void userEvents() throws InterruptedException {
        setUp();

        getUserEvents();
    }

}
