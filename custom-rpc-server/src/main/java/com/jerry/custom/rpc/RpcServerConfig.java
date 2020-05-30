package com.jerry.custom.rpc;

import lombok.Data;

/**
 * Server 配置
 */
@Data
public class RpcServerConfig {
    /**
     * 使用哪个网络模块
     */
    private Class<? extends TransportServer> transportClass = HttpTransportServer.class;

    /**
     * 使用哪个序列化/反序列化实现
     */
    private Class<? extends Encoder> encoderClass = JsonEncoder.class;

    private Class<? extends Decoder> decoderClass = JsonDecoder.class;

    /**
     * server 监听端口号
     */
    private int port = 3000;

}