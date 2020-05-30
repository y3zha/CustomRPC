package com.jerry.custom.rpc;

import java.util.List;

/**
 * 选择哪个 Server 去连接
 */
public interface TransportSelector {

    /**
     * 初始化 selector
     *
     * @param peers 可连接的 server 端点信息
     * @param count client 与 server 可以建立多少个连接
     * @param clazz client 的实现 class
     */
    void init(List<Peer> peers, int count, Class<? extends TransportClient> clazz);

    /**
     * 选择一个 transport 与 server 去做交互
     *
     * @return client
     */
    TransportClient select();

    /**
     * 释放 client
     *
     * @param client client
     */
    void release(TransportClient client);

    /**
     * 关闭 selector
     */
    void close();

}