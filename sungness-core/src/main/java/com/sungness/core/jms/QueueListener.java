package com.sungness.core.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * QueueListener
 * Created by wanghongwei on 6/23/16.
 */
public class QueueListener implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(QueueListener.class);

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage)message;
            try {
                log.info(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }
}