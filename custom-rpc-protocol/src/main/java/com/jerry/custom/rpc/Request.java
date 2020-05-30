package com.jerry.custom.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Request {

    /**
     * 请求的服务
     */
    private ServiceDescriptor service;

    /**
     * 请求参数
     */
    private Object[] parameters;

}