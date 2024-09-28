package com.lego.yxl.msg.controller;

import com.lego.yxl.msg.consumer.AnnotationBasedUserMessageConsumer;
import com.lego.yxl.msg.consumer.UserMessageConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/msg/ann")
public class AnnotationBasedUserMessageConsumerTestController extends UserMessageConsumerSupport {

    @Autowired
    private AnnotationBasedUserMessageConsumer userMessageConsumer;

    protected String getTopic() {
        return "consumer-test-topic-2";
    }

    protected UserMessageConsumer getUserMessageConsumer() {
        return userMessageConsumer;
    }

    @GetMapping("/userEvents")
    public void userEvents() throws InterruptedException {
        setUp();

        getUserEvents();
    }
}
