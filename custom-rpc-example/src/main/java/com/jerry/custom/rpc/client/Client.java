package com.jerry.custom.rpc.client;

import com.jerry.custom.rpc.RpcClient;
import com.jerry.custom.rpc.server.service.CalcService;

public class Client {

    public static void main(String[] args) {
        // 1. 创建client, 拿到远程代理对象
        RpcClient client = new RpcClient();
        CalcService service = client.getProxy(CalcService.class);

        int res1 = service.add(1, 2);
        int res2 = service.minus(1, 2);

        System.out.println(res1);
        System.out.println(res2);

    }
}