package com.jerry.custom.rpc;

import lombok.Data;

/**
 * 响应类, RPC的返回
 */
@Data
public class Response {

    /**
     * 服务返回状态码, 0(成功),非0(失败), 默认为0
     */
    private int code = 0;

    /**
     * 非0, 服务的错误信息
     */
    private String message = "ok";

    /**
     * 服务返回数据
     */
    private Object data;


}