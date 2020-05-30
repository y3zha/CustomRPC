package com.jerry.custom.rpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * 表示一个具体服务
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceInstance {
    /**
     * 服务由哪个对象提供
     */
    private Object target;

    /**
     * 其对应的方法
     */
    private Method method;
}