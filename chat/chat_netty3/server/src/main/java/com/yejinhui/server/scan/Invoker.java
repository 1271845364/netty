package com.yejinhui.server.scan;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ye.jinhui
 * @description 命令执行器
 * @program netty-hello
 * @create 2020/3/21 10:12
 */
public class Invoker {

    /**
     * 目标方法
     */
    private Method method;

    /**
     * 目标对象
     */
    private Object target;

    public static Invoker valueOf(Method method, Object target) {
        Invoker invoker = new Invoker();
        invoker.setMethod(method);
        invoker.setTarget(target);
        return invoker;
    }

    /**
     * 执行
     *
     * @param paramValues
     * @return
     */
    public Object invoke(Object... paramValues) {
        try {
            return method.invoke(target, paramValues);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }
}
