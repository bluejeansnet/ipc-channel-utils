/*
 * Copyright Blue Jeans Network.
 */
package com.bluejeans.utils.ipc;

/**
 * IPC consumer to consume messages from IPC channel with a callback.
 *
 * @author Dinesh Ilindra
 */
public interface IPCConsumer {

    void addHandler(IPCHandler handler);

}
