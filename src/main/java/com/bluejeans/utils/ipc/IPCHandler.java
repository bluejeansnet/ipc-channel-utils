/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * IPC handler
 *
 * @author Dinesh Ilindra
 */
public interface IPCHandler {

    void handleMessage(String mesage);

    void handleMessage(JsonNode ipcEvent);

    void handleCommand(String command, String application, String entity, Object... params);

    void handleCommand(String command, String application, String entity, Map<String, Object> params);
}
