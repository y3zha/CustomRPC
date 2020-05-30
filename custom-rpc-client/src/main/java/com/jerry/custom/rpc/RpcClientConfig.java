package com.jerry.custom.rpc;

import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class RpcClientConfig {

    /**
     * 网络模块实现, 默认 http
     */
    private Class<? extends TransportClient> transportClass = HttpTransportClient.class;

    /**
     * 序列化
     */
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;

    /**
     * 反序列化
     */
    private Class<? extends Decoder> decoderClass = JsonDecoder.class;

    /**
     * 路由选择策略, 默认随机
     */
    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;

    /**
     * 每个 server 的 peer 默认建立一个连接
     */
    private int connectCount = 1;

    /**
     * 能够连接的端点
     */
    private List<Peer> servers = Collections.singletonList(new Peer("127.0.0.1", 3000));



}