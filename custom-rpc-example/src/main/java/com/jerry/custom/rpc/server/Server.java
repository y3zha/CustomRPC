package com.jerry.custom.rpc.server;

import com.jerry.custom.rpc.RpcServer;
import com.jerry.custom.rpc.server.service.CalcService;
import com.jerry.custom.rpc.server.service.impl.CalcServiceImpl;

public class Server {

    public static void main(String[] args) {
        RpcServer server = new RpcServer();
        // 注册服务并启动
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();

    }
}