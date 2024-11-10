package com.lego.yxl.msg.starter;

import com.lego.yxl.msg.core.sender.MessageSender;
import com.lego.yxl.msg.core.sender.ReliableMessageCompensator;
import com.lego.yxl.msg.core.sender.ReliableMessageSender;
import com.lego.yxl.msg.core.sender.support.*;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


public abstract class LocalTableBasedReliableMessageConfigurationSupport {

    @Bean
    public ReliableMessageSender reliableMessageSender(ReliableMessageSendService reliableMessageSendService){
        return new LocalTableBasedReliableMessageSender(reliableMessageSendService);
    }

    @Bean
    public ReliableMessageCompensator reliableMessageCompensator(ReliableMessageSendService reliableMessageSendService){
        return new LocalTableBasedReliableMessageCompensator(reliableMessageSendService);
    }

    @Bean
    public ReliableMessageSendService reliableMessageSendService(LocalMessageRepository localMessageRepository,
                                                                 MessageSender messageSender){
        return new ReliableMessageSendService(localMessageRepository, messageSender);
    }

    @Bean
    public LocalMessageRepository localMessageRepository(){
        return new JdbcTemplateBasedLocalMessageRepository(dataSource(), messageTable());
    }

    @Bean
    public MessageSender messageSender(){
        return createMessageSend();
    }

    protected abstract DataSource dataSource();

    protected abstract String messageTable();

    protected abstract MessageSender createMessageSend();
}