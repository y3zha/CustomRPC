package com.jerry.custom.rpc;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理 Service 暴露的服务
 * 1. 注册服务
 * 2. 查找服务
 */
@Slf4j
public class ServiceManager {

    /**
     * 1. 注册服务 服务描述 -> 具体实现
     */
    private Map<ServiceDescriptor, ServiceInstance> services;

    public ServiceManager() {
        this.services = new ConcurrentHashMap<>();
    }

    /**
     * @param interfaceClass 服务注册，指定接口
     * @param bean           接口实现的具体子类对象
     */
    public <T> void register(Class<T> interfaceClass, T bean) {
        // 1. 扫描接口中所有方法并绑定到 bean
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method : publicMethods) {
            ServiceInstance instance = new ServiceInstance(bean, method);
            ServiceDescriptor descriptor = ServiceDescriptor.from(interfaceClass, method);
            services.put(descriptor, instance);
            log.info("register service:{} {}", descriptor.getClazz(), descriptor.getMethod());
        }
    }

    /**
     * @param request 服务查找请求
     * @return 具体的服务 instance
     */
    public ServiceInstance lookup(Request request) {
        ServiceDescriptor descriptor = request.getService();
        // 需要重写 equals 和 hashcode
        return services.get(descriptor);
    }
}