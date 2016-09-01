/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bluejeans.utils.MetaUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Basic IPC handler.
 *
 * @author Dinesh Ilindra
 */
public abstract class IPCBasicHandler implements IPCHandler {

    private final Logger logger = LoggerFactory.getLogger(IPCBasicHandler.class);

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * @return the mapper
     */
    public ObjectMapper getMapper() {
        return mapper;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bjn.jsoneventprocessor.ipc.IPCHandler#handleMessage(java.lang.String)
     */
    @Override
    public void handleMessage(final String message) {
        try {
            handleMessage(mapper.readTree(message));
        } catch (final IOException ex) {
            logger.error("Problem parsing from JSON", ex);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bjn.jsoneventprocessor.ipc.IPCHandler#handleMessage(com.fasterxml
     * .jackson.databind.JsonNode)
     */
    @Override
    public void handleMessage(final JsonNode ipcEvent) {
        final Map<String, Object> paramsMap = new HashMap<>();
        final Iterator<Entry<String, JsonNode>> iter = ipcEvent.get("params").fields();
        while (iter.hasNext()) {
            final Entry<String, JsonNode> entry = iter.next();
            try {
                paramsMap.put(entry.getKey(), mapper.treeToValue(entry.getValue(), Object.class));
            } catch (final JsonProcessingException ex) {
                logger.warn("Problem in reading this value - " + entry.getValue(), ex);
            }
        }
        handleCommand(ipcEvent.get("command").asText(), ipcEvent.get("application").asText(), ipcEvent.get("entity")
                .asText(), paramsMap);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bjn.jsoneventprocessor.ipc.IPCHandler#handleCommand(java.lang.String,
     * java.lang.String, java.lang.String, java.lang.Object[])
     */
    @Override
    public void handleCommand(final String command, final String application, final String entity,
            final Object... params) {
        handleCommand(command, application, entity, MetaUtil.createParamMap(params));
    }

}
