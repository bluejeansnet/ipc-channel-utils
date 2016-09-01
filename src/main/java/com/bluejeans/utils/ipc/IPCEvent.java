/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * IPC event.
 *
 * @author Dinesh Ilindra
 */
public class IPCEvent {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static final String EVENT_TYPE = "__COMMAND";

    public static IPCEvent valueOf(final String eventStr) {
        try {
            return mapper.readValue(eventStr, IPCEvent.class);
        } catch (final IOException e) {
            return new IPCEvent();
        }
    }

    private String event = EVENT_TYPE;
    private String senderUuid, command, application, entity;
    private Map<String, Object> params;

    public IPCEvent() {
        params = new HashMap<>();
    }

    public IPCEvent(final String senderUuid, final String command, final String application, final String entity,
            final Map<String, Object> params) {
        this.senderUuid = senderUuid;
        this.command = command;
        this.application = application;
        this.entity = entity;
        this.params = params;
    }

    @Override
    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            return "";
        }
    }

    public void addParam(final String key, final String value) {
        params.put(key, value);
    }

    /**
     * @return the event
     */
    public String getEvent() {
        return event;
    }

    /**
     * @param event
     *            the event to set
     */
    public void setEvent(final String event) {
        this.event = event;
    }

    /**
     * @return the senderUuid
     */
    public String getSenderUuid() {
        return senderUuid;
    }

    /**
     * @param senderUuid
     *            the senderUuid to set
     */
    public void setSenderUuid(final String senderUuid) {
        this.senderUuid = senderUuid;
    }

    /**
     * @return the command
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command
     *            the command to set
     */
    public void setCommand(final String command) {
        this.command = command;
    }

    /**
     * @return the application
     */
    public String getApplication() {
        return application;
    }

    /**
     * @param application
     *            the application to set
     */
    public void setApplication(final String application) {
        this.application = application;
    }

    /**
     * @return the entity
     */
    public String getEntity() {
        return entity;
    }

    /**
     * @param entity
     *            the entity to set
     */
    public void setEntity(final String entity) {
        this.entity = entity;
    }

    /**
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * @param params
     *            the params to set
     */
    public void setParams(final Map<String, Object> params) {
        this.params = params;
    }

}
