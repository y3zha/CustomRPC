package com.jerry.custom.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * 表示网络传输的一个端点
 */
@Data
@AllArgsConstructor
public class Peer {
    /**
     * host
     */
    private String host;

    /**
     * 端口
     */
    private int port;

}