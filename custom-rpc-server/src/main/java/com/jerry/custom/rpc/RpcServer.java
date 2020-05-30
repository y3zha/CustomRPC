package com.jerry.custom.rpc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j

public class RpcServer {

    private RpcServerConfig config;

    private TransportServer transportServer;

    private Encoder encoder;

    private Decoder decoder;

    private ServiceManager serviceManager;

    private ServiceInvoker serviceInvoker;

    public RpcServer() {
        this(new RpcServerConfig());
    }

    public RpcServer(RpcServerConfig config) {
        this.config = config;
        this.transportServer = ReflectionUtils.newInstance(config.getTransportClass());
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.serviceManager = new ServiceManager();
        this.serviceInvoker = new ServiceInvoker();

        // 初始化 transport server
        this.transportServer.init(config.getPort(), this.handler);

    }

    /**
     * @param interfaceClass 服务注册，指定接口
     * @param bean           接口实现的具体子类对象
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start() {
        this.transportServer.start();

    }

    public void stop() {
        this.transportServer.stop();

    }

    /**
     * 处理请求，分成三个步骤
     */
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {
            Response response = new Response();
            try {
                // 1. request 中读到二进制数据并反序列化
                byte[] bytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(bytes, Request.class);
                log.info("get request: {}", request);

                // 2. 通过 request 找到服务, 由 serviceInvoker 去调用
                ServiceInstance serviceInstance = serviceManager.lookup(request);
                Object res = serviceInvoker.invoke(serviceInstance, request);

                // 3. response 设置调用结果
                response.setData(res);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                response.setCode(1);
                response.setMessage("RpcServer got error: " + e.getClass().getName() + " : " + e.getMessage());
            } finally {
                try {
                    byte[] resp = encoder.encode(response);
                    toResp.write(resp);
                    log.info("response client");
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    e.printStackTrace();
                }
            }
        }
    };
}