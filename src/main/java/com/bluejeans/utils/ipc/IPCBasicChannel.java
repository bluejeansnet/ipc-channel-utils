/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluejeans.utils.EnumCounter;
import com.bluejeans.utils.MetaUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Basic IPC channel.
 *
 * @author Dinesh Ilindra
 */
public abstract class IPCBasicChannel implements IPCChannel {

    private static final Logger logger = LoggerFactory.getLogger(IPCBasicChannel.class);

    private final String channelId = java.util.UUID.randomUUID().toString();

    private final ObjectMapper mapper = new ObjectMapper();

    private final List<IPCHandler> handlers = new ArrayList<>();

    private boolean sendToMyself = false;

    private final EnumCounter<IPCEventType> messageCounts = new EnumCounter<>(IPCEventType.class);

    /*
     * (non-Javadoc)
     *
     * @see com.bjn.jsoneventprocessor.ipc.IPCConsumer#addHandler(com.bjn.
     * jsoneventprocessor.ipc.IPCHandler)
     */
    @Override
    public void addHandler(final IPCHandler handler) {
        handlers.add(handler);
    }

    /**
     * Handle through all handlers.
     *
     * @param message
     */
    public void handleMessage(final String message) {
        try {
            final JsonNode event = mapper.readTree(message);
            if (sendToMyself || !channelId.equals(event.get(SENDER_UUID_KEY).asText())) {
                messageCounts.incrementEventCount(IPCEventType.MESSAGE_PROCESSED);
                for (final IPCHandler handler : handlers) {
                    try {
                        handler.handleMessage(event);
                    } catch (final RuntimeException re) {
                        logger.error("Problem in handler - " + handler, re);
                    }
                }
            }
        } catch (final IOException ex) {
            logger.error("Problem in parsing the JSON message", ex);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.bjn.jsoneventprocessor.ipc.IPCProducer#sendCommand(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.Object[])
     */
    @Override
    public void sendCommand(final String command, final String application, final String entity,
            final Object... params) {
        sendCommand(command, application, entity, MetaUtil.createParamMap(params));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.bjn.jsoneventprocessor.ipc.IPCProducer#sendCommand(java.lang.String,
     * java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public void sendCommand(final String command, final String application, final String entity,
            final Map<String, Object> params) {
        final ObjectNode dataNode = mapper.createObjectNode();
        dataNode.put("command", command);
        dataNode.put("application", application);
        dataNode.put("entity", entity);
        dataNode.put("event", COMMAND_EVENT_TYPE);
        final ObjectNode paramsNode = dataNode.putObject("params");
        for (final Entry<String, Object> entry : params.entrySet()) {
            paramsNode.set(entry.getKey(), mapper.valueToTree(entry.getValue()));
        }
        produceMessage(dataNode);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.bjn.jsoneventprocessor.ipc.IPCProducer#produceMessage(com.fasterxml
     * .jackson.databind.JsonNode)
     */
    @Override
    public void produceMessage(final JsonNode ipcEvent) {
        ObjectNode dataNode = null;
        if (ipcEvent.isObject()) {
            dataNode = (ObjectNode) ipcEvent;
        } else {
            dataNode = mapper.createObjectNode();
            dataNode.set("data", ipcEvent);
        }
        dataNode.put(SENDER_UUID_KEY, channelId);
        produceMessage(dataNode.toString());
    }

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @return the handlers
     */
    public List<IPCHandler> getHandlers() {
        return handlers;
    }

    /**
     * @return the mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * @return the messageCounts
     */
    public EnumCounter<IPCEventType> getMessageCounts() {
        return messageCounts;
    }

    /**
     * @return the sendToMyself
     */
    public boolean isSendToMyself() {
        return sendToMyself;
    }

    /**
     * @param sendToMyself
     *            the sendToMyself to set
     */
    public void setSendToMyself(final boolean sendToMyself) {
        this.sendToMyself = sendToMyself;
    }

}
