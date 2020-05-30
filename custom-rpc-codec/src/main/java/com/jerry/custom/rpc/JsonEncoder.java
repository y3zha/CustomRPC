package com.jerry.custom.rpc;

import com.alibaba.fastjson.JSON;

public class JsonEncoder implements Encoder {

    @Override
    public byte[] encode(Object object) {
        return JSON.toJSONBytes(object);
    }

}