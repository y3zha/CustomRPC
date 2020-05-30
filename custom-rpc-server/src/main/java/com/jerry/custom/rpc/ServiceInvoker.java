package com.jerry.custom.rpc;

/**
 * 调用 service 具体服务实例的类
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance, Request request) {
        return ReflectionUtils.invoke(
                serviceInstance.getTarget(),
                serviceInstance.getMethod(),
                request.getParameters());
    }
}