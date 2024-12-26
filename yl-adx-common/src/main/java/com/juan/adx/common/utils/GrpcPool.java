package com.juan.adx.common.utils;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Kevin.赵伟凯
 * @CreateTime: 2024-12-23 12:33
 * @Description:
 * @Version: 1.0
 */

public class GrpcPool {

    private final ConcurrentLinkedQueue<ManagedChannel> pool = new ConcurrentLinkedQueue<>();
    private final String host;
    private final int port;

    public GrpcPool(String host, int port, int initialConnections) {
        this.host = host;
        this.port = port;
        for (int i = 0; i < initialConnections; i++) {
            pool.add(ManagedChannelBuilder.forAddress(host, port).usePlaintext().build());
        }
    }

    public ManagedChannel borrowChannel() {
        ManagedChannel channel = pool.poll();
        if (channel == null) {
            channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        }

        try {
            channel.awaitTermination(300L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return channel;
    }

    public void returnChannel(ManagedChannel channel) {
        pool.offer(channel);
    }
}
