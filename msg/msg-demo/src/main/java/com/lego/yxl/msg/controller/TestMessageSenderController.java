package com.lego.yxl.msg.controller;

import com.lego.yxl.msg.core.sender.Message;
import com.lego.yxl.msg.core.sender.ReliableMessageCompensator;
import com.lego.yxl.msg.sender.TestMessageSender;
import com.lego.yxl.msg.sender.TestMessageSenderService;
import com.lego.yxl.msg.core.sender.support.LocalMessage;
import com.lego.yxl.msg.core.sender.support.LocalMessageRepository;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/msg")
public class TestMessageSenderController {

    @Autowired
    private TestMessageSenderService testMessageSenderService;
    @Autowired
    private TestMessageSender testMessageSender;

    @Autowired
    private ReliableMessageCompensator reliableMessageCompensator;

    @Autowired
    private LocalMessageRepository localMessageRepository;

    public void setUp() {
        this.testMessageSender.clean();
        this.testMessageSender.cleanError();
    }

    public void tearDown() {
        this.testMessageSender.clean();
        this.testMessageSender.cleanError();
    }

    @RequestMapping("/testNoTransaction")
    public void testNoTransaction() {
        setUp();

        this.testMessageSenderService.testNoTransaction();

        {
            List<Message> messages = this.testMessageSender.getMessages();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
        }

        this.testMessageSender.clean();
        boolean error = false;
        try {
            this.testMessageSenderService.testNoTransactionError();
        } catch (Exception e) {
            error = true;
        }

        Assertions.assertTrue(error);

        {
            List<Message> messages = this.testMessageSender.getMessages();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
        }

        tearDown();
    }

    @RequestMapping("/testTestSuccess")
    public void testTestSuccess() {
        setUp();

        this.testMessageSenderService.testSuccess();

        List<Message> messages = this.testMessageSender.getMessages();
        Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));

        tearDown();
    }

    @RequestMapping("/testTestError")
    public void testTestError() {
        setUp();

        boolean error = false;
        try {
            this.testMessageSenderService.testError();
        } catch (Exception e) {
            error = true;
        }

        Assertions.assertTrue(error);

        List<Message> messages = this.testMessageSender.getMessages();
        Assertions.assertTrue(CollectionUtils.isEmpty(messages));

        tearDown();
    }

    @RequestMapping("/loadAndSend")
    public void loadAndSend() throws InterruptedException {
        setUp();

        // 处理消费表中待发送数据
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -120), 1000);

        // 进行 error 标记， MessageSender 发送请求直接失败
        this.testMessageSender.markError();
        for (int i = 0; i < 10; i++) {
            // 执行业务逻辑，业务逻辑不受影响
            this.testMessageSenderService.testSuccess();
        }
        // 清理 error 标记，MessageSender 正常发送
        this.testMessageSender.cleanError();


        {
            // 检测消息表中存在待处理的任务
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(10, localMessages.size());
        }

        // 对消息进行补充处理
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -60), 5);

        {
            //  由于时间限制，未处理消息表的任务
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(10, localMessages.size());
        }

        // 等待时间超时
        TimeUnit.SECONDS.sleep(15);

        this.testMessageSender.clean();
        // 对消息进行补充处理
        this.reliableMessageCompensator.compensate(DateUtils.addSeconds(new Date(), -60), 50);

        {
            //  成功处理消息表的待处理任务
            List<LocalMessage> localMessages = localMessageRepository.loadNotSuccessByUpdateGt(DateUtils.addSeconds(new Date(), -60), 100);
            Assertions.assertEquals(0, localMessages.size());

            List<Message> messages = this.testMessageSender.getMessages();
            Assertions.assertTrue(CollectionUtils.isNotEmpty(messages));
        }

        tearDown();
    }

}
