package com.jerry.custom.rpc;

public interface Decoder {

    <T> T decode(byte[] bytes, Class<T> clazz);

}
