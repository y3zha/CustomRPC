package com.jerry.custom.rpc;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
public class RandomTransportSelector implements TransportSelector {

    /**
     * 存放所有已经连好的 transport client
     */
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        this.clients = new CopyOnWriteArrayList<>();
    }

    @Override
    public void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz) {
        // count 必须大于等于1
        count = Math.max(count, 1);
        for (Peer peer : peers) {
            for (int i = 0; i < count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(peer);
                clients.add(client);
            }
            log.info("connect server: {}", peer);
        }
    }

    @Override
    public TransportClient select() {
        // 从 clients 池里随机取一个返回
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public void close() {
        // 关闭所有 client
        for (TransportClient client : clients) {
            client.close();
        }
        clients.clear();
    }
}