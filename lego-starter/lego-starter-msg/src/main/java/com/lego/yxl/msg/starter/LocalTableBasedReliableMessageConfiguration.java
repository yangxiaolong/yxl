package com.lego.yxl.msg.starter;

import com.lego.yxl.core.msg.sender.MessageSender;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class LocalTableBasedReliableMessageConfiguration
        extends LocalTableBasedReliableMessageConfigurationSupport {

    @Resource
    private DataSource dataSource;

    @Autowired
    private MessageSender messageSender;

    @Override
    protected DataSource dataSource() {
        return this.dataSource;
    }

    @Override
    protected String messageTable() {
        return "test_message";
    }

    @Override
    protected MessageSender createMessageSend() {
        return this.messageSender;
    }

}
