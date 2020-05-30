package com.jerry.custom.rpc;

/**
 * 序列化
 */
public interface Encoder {

    byte[] encode(Object object);
}
