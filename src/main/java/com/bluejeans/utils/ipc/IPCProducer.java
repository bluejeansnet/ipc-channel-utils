/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * IPC producer to produce messages to IPC channel.
 *
 * @author Dinesh Ilindra
 */
public interface IPCProducer {

    String SENDER_UUID_KEY = "senderUuid";

    String COMMAND_EVENT_TYPE = "__COMMAND";

    void produceMessage(String message);

    void produceMessage(JsonNode ipcEvent);

    void sendCommand(String command, String application, String entity, Map<String, Object> params);

    void sendCommand(String command, String application, String entity, Object... params);
}
