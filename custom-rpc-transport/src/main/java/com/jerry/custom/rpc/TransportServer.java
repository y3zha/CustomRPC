package com.jerry.custom.rpc;

/**
 * RPC server
 * 1. 启动并监听客户端请求
 * 2. 接收请求
 * 3. 关闭监听
 */
public interface TransportServer {

    void init(int port, RequestHandler handler);

    void start();

    void stop();
}