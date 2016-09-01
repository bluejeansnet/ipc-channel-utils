/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc.impl;

import java.nio.charset.Charset;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;

import com.bluejeans.utils.ipc.IPCBasicChannel;
import com.bluejeans.utils.ipc.IPCEventType;

/**
 * RabbitMQ IPC channel.
 *
 * @author Dinesh Ilindra
 */
public class RabbitIPCChannel extends IPCBasicChannel implements MessageListener {

    private AmqpTemplate amqpTemplate;

    private String exchangeName;

    private String routingKey = "";

    private final MessageProperties emptyProperties = MessagePropertiesBuilder.newInstance().build();

    /*
     * (non-Javadoc)
     *
     * @see com.bjn.jsoneventprocessor.ipc.IPCProducer#produceMessage(java.lang.String )
     */
    @Override
    public void produceMessage(final String message) {
        getMessageCounts().incrementEventCount(IPCEventType.MESSAGE_SENT);
        amqpTemplate.send(exchangeName, routingKey,
                new Message(message.toString().getBytes(Charset.defaultCharset()), emptyProperties));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.amqp.core.MessageListener#onMessage(org.springframework
     * .amqp.core.Message)
     */
    @Override
    public void onMessage(final Message message) {
        getMessageCounts().incrementEventCount(IPCEventType.MESSAGE_RECIEVED);
        handleMessage(new String(message.getBody()));
    }

    /**
     * @return the exchangeName
     */
    public String getExchangeName() {
        return exchangeName;
    }

    /**
     * @param exchangeName
     *            the exchangeName to set
     */
    public void setExchangeName(final String exchangeName) {
        this.exchangeName = exchangeName;
    }

    /**
     * @return the routingKey
     */
    public String getRoutingKey() {
        return routingKey;
    }

    /**
     * @param routingKey
     *            the routingKey to set
     */
    public void setRoutingKey(final String routingKey) {
        this.routingKey = routingKey;
    }

    /**
     * @return the amqpTemplate
     */
    public AmqpTemplate getAmqpTemplate() {
        return amqpTemplate;
    }

    /**
     * @param amqpTemplate
     *            the amqpTemplate to set
     */
    public void setAmqpTemplate(final AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

}
