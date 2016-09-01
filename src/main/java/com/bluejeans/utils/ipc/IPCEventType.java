/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

/**
 * Represents any event in the processor.
 *
 * @author Dinesh Ilindra
 */
public enum IPCEventType {

    /**
     * An event is received
     */
    TOTAL_EVENTS,

    /**
     * Internal event
     */
    INTERNAL_EVENT,

    /**
     * Event requeue
     */
    EVENT_REQUEUE,

    /**
     * Handler used
     */
    HANDLER_USED,

    /**
     * Exception
     */
    EXCEPTION,

    /**
     * Message sent
     */
    MESSAGE_SENT,

    /**
     * Message recieved
     */
    MESSAGE_RECIEVED,

    /**
     * Message processed
     */
    MESSAGE_PROCESSED,

}
