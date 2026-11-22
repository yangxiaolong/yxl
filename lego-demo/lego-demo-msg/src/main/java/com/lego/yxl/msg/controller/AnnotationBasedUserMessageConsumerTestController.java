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

    @Override
    protected String getTopic() {
        return "consumer-test-topic-2";
    }

    protected UserMessageConsumer getUserMessageConsumer() {
        return userMessageConsumer;
    }

    @GetMapping("/userEvents")
    public void userEvents() throws InterruptedException {
        setUp();

        //5 对每个用户断言：收到 4 条，且顺序必须是 创建 → 启用 → 停用 → 注销。
        getUserEvents();
    }
}
