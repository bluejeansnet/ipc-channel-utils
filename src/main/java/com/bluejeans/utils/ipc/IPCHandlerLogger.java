/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Just print the messages as they are recieved.
 *
 * @author Dinesh Ilindra
 */
public class IPCHandlerLogger extends IPCBasicHandler {

    private final Logger logger = LoggerFactory.getLogger(IPCHandlerLogger.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * com.bjn.jsoneventprocessor.ipc.IPCHandler#handleCommand(java.lang.String,
     * java.lang.String, java.lang.String, java.util.Map)
     */
    @Override
    public void handleCommand(final String command, final String application, final String entity,
            final Map<String, Object> params) {
        logger.info(command + " -> " + application + " -> " + entity + " -> " + params);
    }

}
