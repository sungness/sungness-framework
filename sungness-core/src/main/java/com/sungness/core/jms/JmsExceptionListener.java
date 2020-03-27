package com.sungness.core.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

/**
 * JmsExceptionListener
 * Created by wanghongwei on 6/23/16.
 */
public class JmsExceptionListener implements ExceptionListener {

    private static final Logger log = LoggerFactory.getLogger(JmsExceptionListener.class);

    @Override
    public void onException(JMSException e) {
        log.warn("Jms Listener throw exception:" + e.getMessage());
        e.printStackTrace();
    }
}