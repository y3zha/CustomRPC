package com.jerry.custom.rpc.server.service.impl;

import com.jerry.custom.rpc.server.service.CalcService;

public class CalcServiceImpl implements CalcService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}