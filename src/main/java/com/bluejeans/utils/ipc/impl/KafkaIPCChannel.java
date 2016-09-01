/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import com.bluejeans.kafka.KafkaProcessorContext;
import com.bluejeans.kafka.KafkaRecordProcessor;
import com.bluejeans.kafka.SimpleKafkaProducer;
import com.bluejeans.utils.ipc.IPCBasicChannel;
import com.bluejeans.utils.ipc.IPCEventType;

/**
 * Kafka IPC channel.
 *
 * @author Dinesh Ilindra
 */
public class KafkaIPCChannel extends IPCBasicChannel implements KafkaRecordProcessor<String, String> {

    private SimpleKafkaProducer<String, String> kafkaProducer;

    private String topicName;

    private String routingKey = "";

    /*
     * (non-Javadoc)
     *
     * @see com.bjn.jsoneventprocessor.ipc.IPCProducer#produceMessage(java.lang.String )
     */
    @Override
    public void produceMessage(final String message) {
        getMessageCounts().incrementEventCount(IPCEventType.MESSAGE_SENT);
        kafkaProducer.send(topicName, routingKey, message);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bluejeans.kafka.KafkaRecordProcessor#processKafkaRecord(org.apache.kafka.clients.consumer
     * .ConsumerRecord, com.bluejeans.kafka.KafkaProcessorContext)
     */
    @Override
    public void processKafkaRecord(final ConsumerRecord<String, String> record,
            final KafkaProcessorContext<String, String> context) {
        getMessageCounts().incrementEventCount(IPCEventType.MESSAGE_RECIEVED);
        handleMessage(record.value());
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
     * @return the kafkaProducer
     */
    public SimpleKafkaProducer<String, String> getKafkaProducer() {
        return kafkaProducer;
    }

    /**
     * @param kafkaProducer
     *            the kafkaProducer to set
     */
    public void setKafkaProducer(final SimpleKafkaProducer<String, String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    /**
     * @return the topicName
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName
     *            the topicName to set
     */
    public void setTopicName(final String topicName) {
        this.topicName = topicName;
    }

}
