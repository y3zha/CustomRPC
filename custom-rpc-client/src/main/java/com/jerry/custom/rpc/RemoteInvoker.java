package com.jerry.custom.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private Class<?> clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;

    /**
     * 构造方法
     *
     * @param clazz    远程服务 clazz 信息
     * @param encoder  远程服务序列化
     * @param decoder  远程服务反序列化
     * @param selector 远程服务网络连接 selector
     */
    public RemoteInvoker(Class<?> clazz,
                         Encoder encoder,
                         Decoder decoder,
                         TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1. 发送请求，等待响应
        Request request = new Request();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setParameters(args);

        // 2. 拿到响应数据，结束等待
        Response response = invokeRemote(request);
        if (response == null || response.getCode() != 0) {
            throw new IllegalStateException("fail to invoke remote: " + response);
        }
        return response.getData();
    }

    private Response invokeRemote(Request request) {
        TransportClient client = null;
        Response response;
        try {
            // 1. 选择一个server 并发送数据
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream is = client.write(new ByteArrayInputStream(outBytes));

            // 2. 拿到 server 返回的数据
            byte[] inBytes = IOUtils.readFully(is, is.available());
            response = decoder.decode(inBytes, Response.class);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            response = new Response();
            response.setCode(1);
            response.setMessage("RpcClient got error: " + e.getClass() + " : " + e.getMessage());
        }
        selector.release(client);
        return response;
    }

}