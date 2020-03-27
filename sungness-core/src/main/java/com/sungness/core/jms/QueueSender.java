package com.sungness.core.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Message;

/**
 * QueueSender
 * Created by wanghongwei on 6/23/16.
 */
public class QueueSender {
    private static final Logger log = LoggerFactory.getLogger(QueueSender.class);

    private final JmsTemplate jmsTemplate;

    public QueueSender(JmsTemplate jmsTemplate ) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String message) {
        log.info("begin send jms:" + message);
        jmsTemplate.convertAndSend("Queue.crawler", message);
    }

    public void receive() {
        log.info("begin receive jms from:Queue.crawler");
        Message message = jmsTemplate.receive("Queue.crawler");
        log.info(message.toString());
    }
}
