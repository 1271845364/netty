package com.yejinhui.spring;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 22:33
 */
public class InvokerHolder {

    public static Map<Short, Map<Short, Invoker>> invokers = new HashMap<>();

    /**
     * 添加一个执行器
     *
     * @param module
     * @param cmd
     * @param invoker
     */
    public static void addInvoker(short module, short cmd, Invoker invoker) {
        Map<Short, Invoker> map = invokers.get(module);
        if (map == null) {
            map = new HashMap<>(16);
            invokers.put(module, map);
        }
        map.put(cmd, invoker);
    }

    /**
     * 获取的一个执行器
     *
     * @param module
     * @param cmd
     * @return
     */
    public static Invoker getInvoker(short module, short cmd) {
        Map<Short, Invoker> map = invokers.get(module);
        if (map == null) {
            return null;
        }
        return map.get(cmd);
    }

}
