package com.jerry.custom.rpc;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 */
public class ReflectionUtils {
    private ReflectionUtils() {
    }

    /**
     * 根据 clazz 创建对象, 转成期望的类型
     *
     * @param clazz 待创建对象的类
     * @param <T> 对象类型
     * @return 对象
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取这个类所有的公共方法
     *
     * @param clazz 类
     * @return
     */
    public static <T> Method[] getPublicMethods(Class<T> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pMethods = new ArrayList<>();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                pMethods.add(method);
            }
        }
        return pMethods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的某个方法，如果是静态方法，它不属于某个对象
     *
     * @param obj    被调用方法的对象吧
     * @param method 被调用的方法
     * @param args   方法参数
     * @return 返回结果
     */
    public static Object invoke(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}