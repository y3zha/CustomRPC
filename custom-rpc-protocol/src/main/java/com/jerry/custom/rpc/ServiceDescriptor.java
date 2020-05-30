package com.jerry.custom.rpc;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 服务描述
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDescriptor {

    /**
     * 服务类名
     */
    private String clazz;

    /**
     * 服务方法名
     */
    private String method;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     * 返回参数类型
     */
    private String[] parameterTypes;

    public static <T> ServiceDescriptor from(Class<T> clazz, Method method) {
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setClazz(clazz.getName());
        descriptor.setMethod(method.getName());
        descriptor.setReturnType(method.getReturnType().getName());

        Class<?>[] parameterClasses = method.getParameterTypes();
        String[] parameterTypes = new String[parameterClasses.length];
        for (int i = 0; i < parameterClasses.length; i++) {
            parameterTypes[i] = parameterClasses[i].getName();
        }
        descriptor.setParameterTypes(parameterTypes);

        return descriptor;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        ServiceDescriptor that = (ServiceDescriptor) o;
        return this.toString().equals(that.toString());
    }

    @Override
    public String toString() {
        return String.format("clazz=%s, method=%s, returnType=%s, parameterTypes=%s",
                clazz, method, returnType, Arrays.toString(parameterTypes));
    }

}