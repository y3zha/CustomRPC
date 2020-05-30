package com.jerry.custom.rpc;

import com.alibaba.fastjson.JSON;

public class JsonDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}