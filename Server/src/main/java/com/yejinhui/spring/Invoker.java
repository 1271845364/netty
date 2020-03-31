package com.yejinhui.spring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 执行器
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 22:23
 */
public class Invoker {

    /**
     * 目标对象
     */
    private Object target;

    /**
     * 方法
     */
    private Method method;

    /**
     * 获取Invoker实例
     *
     * @param target
     * @param method
     * @return
     */
    public static Invoker valueOf(Object target, Method method) {
        Invoker invoker = new Invoker();
        invoker.setTarget(target);
        invoker.setMethod(method);
        return invoker;
    }

    /**
     * 执行
     *
     * @param args
     * @return
     */
    public Object invoke(Object[] args) {
        try {
            return method.invoke(target, args);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
