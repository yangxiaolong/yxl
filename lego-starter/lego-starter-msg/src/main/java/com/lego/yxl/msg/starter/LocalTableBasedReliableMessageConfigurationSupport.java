package com.lego.yxl.msg.starter;

import com.lego.yxl.core.msg.sender.MessageSender;
import com.lego.yxl.core.msg.sender.ReliableMessageCompensator;
import com.lego.yxl.core.msg.sender.ReliableMessageSender;
import com.lego.yxl.core.msg.sender.support.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.support.JdbcTransactionManager;

import javax.sql.DataSource;

public abstract class LocalTableBasedReliableMessageConfigurationSupport {

    @Bean
    public ReliableMessageSender reliableMessageSender(ReliableMessageSendService reliableMessageSendService) {
        return new LocalTableBasedReliableMessageSender(reliableMessageSendService);
    }

    @Bean
    public ReliableMessageCompensator reliableMessageCompensator(ReliableMessageSendService reliableMessageSendService) {
        return new LocalTableBasedReliableMessageCompensator(reliableMessageSendService);
    }

    @Bean
    public ReliableMessageSendService reliableMessageSendService(LocalMessageRepository localMessageRepository,
                                                                 MessageSender messageSender) {
        return new ReliableMessageSendService(localMessageRepository, messageSender);
    }

    @Bean
    @Primary
    public JdbcTransactionManager transactionManager() {
        return new JdbcTransactionManager(dataSource());
    }

    @Bean
    public LocalMessageRepository localMessageRepository(JdbcTransactionManager jdbcTransactionManager) {
        return new JdbcTemplateBasedLocalMessageRepository(dataSource(), messageTable(), jdbcTransactionManager);
    }

    @Bean
    public MessageSender messageSender() {
        return createMessageSend();
    }

    protected abstract DataSource dataSource();

    protected abstract String messageTable();

    protected abstract MessageSender createMessageSend();
}