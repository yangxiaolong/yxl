package com.lego.yxl.core.msg.sender.support;

import com.lego.yxl.core.msg.sender.Message;
import com.lego.yxl.core.msg.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Comparator;
import java.util.Date;
import java.util.List;


@Slf4j
public class ReliableMessageSendService {
    private final LocalMessageRepository localMessageRepository;
    private final MessageSender messageSender;

    public ReliableMessageSendService(LocalMessageRepository localMessageRepository,
                                      MessageSender messageSender) {
        this.localMessageRepository = localMessageRepository;
        this.messageSender = messageSender;
    }

    /**
     * 加载未发送的消息，进行重新发送
     *
     * @param startDate   startDate
     * @param sizePreTask sizePreTask
     */
    public void loadAndResend(Date startDate, int sizePreTask) {
        Date latestUpdateTime = startDate;
        List<LocalMessage> localMessages = this.localMessageRepository.loadNotSuccessByUpdateGt(latestUpdateTime, sizePreTask);
        while (CollectionUtils.isNotEmpty(localMessages)) {
            log.info("load {} task by {} to resend", localMessages.size(), latestUpdateTime);

            retrySend(localMessages);

            latestUpdateTime = calLatestUpdateTime(localMessages);
            log.info("next time is {}", latestUpdateTime);

            localMessages = this.localMessageRepository.loadNotSuccessByUpdateGt(latestUpdateTime, sizePreTask);
        }
        log.info("End to load task by {}", startDate);
    }

    public void saveAndSend(Message message) {
        LocalMessage localMessage = convertToLocalMessage(message);

        saveToDB(localMessage);

        SendMessageTask sendMessageTask = buildTask(localMessage);

        addCallbackOrRunTask(sendMessageTask);
    }

    private void addCallbackOrRunTask(SendMessageTask sendMessageTask) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            // 添加监听器，在事务提交后触发后续任务
            TransactionSynchronization transactionSynchronization = new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    sendMessageTask.run();
                }
            };
            TransactionSynchronizationManager.registerSynchronization(transactionSynchronization);
            log.info("success to register synchronization for message {}", sendMessageTask.getLocalMessage());
        } else {
            // 没有可以事务，直接触发后续任务
            log.info("No Transaction !!! begin to run task for message {}", sendMessageTask.getLocalMessage());
            sendMessageTask.run();
            log.info("No Transaction !!! success to run task for message {}", sendMessageTask.getLocalMessage());
        }
    }

    private SendMessageTask buildTask(LocalMessage localMessage) {
        return new SendMessageTask(this.localMessageRepository, this.messageSender, localMessage);
    }

    private void saveToDB(LocalMessage localMessage) {
        this.localMessageRepository.save(localMessage);
    }

    private LocalMessage convertToLocalMessage(Message message) {
        return LocalMessage.apply(message);
    }

    private Date calLatestUpdateTime(List<LocalMessage> localMessages) {
        return localMessages.stream()
                .map(LocalMessage::getUpdateTime)
                .max(Comparator.naturalOrder())
                .orElse(new Date());
    }

    private void retrySend(List<LocalMessage> localMessages) {
        Date now = new Date();
        localMessages.stream()
                .filter(message -> message.needRetry(now))
                .map(localMessage -> new SendMessageTask(this.localMessageRepository, messageSender, localMessage))
                .forEach(SendMessageTask::run);
    }

}
