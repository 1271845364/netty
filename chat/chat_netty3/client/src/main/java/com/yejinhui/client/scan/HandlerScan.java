package com.yejinhui.client.scan;

import com.yejinhui.common.core.annotation.SocketCommand;
import com.yejinhui.common.core.annotation.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author ye.jinhui
 * @description handler扫描器
 * @program netty-hello
 * @create 2020/3/21 10:26
 */
@Component
public class HandlerScan implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Class<?> clazz = o.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            //扫描类的所有接口
            for (Class<?> anInterface : interfaces) {
                //判断是否为handler接口类
                SocketModule socketModule = anInterface.getAnnotation(SocketModule.class);
                if (socketModule == null) {
                    continue;
                }

                //找出命令方法
                Method[] methods = anInterface.getMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        SocketCommand socketCommand = method.getAnnotation(SocketCommand.class);
                        if (socketCommand == null) {
                            continue;
                        }

                        final short module = socketModule.module();
                        final short cmd = socketCommand.command();

                        //添加Invoker到InvokerHolder，相当于给InvokerHolder进行初始化
                        if (InvokerHolder.getInvoker(module, cmd) == null) {
                            InvokerHolder.addInvoker(module, cmd, new Invoker());
                        } else {
                            System.out.println("重复命令：" + "module:" + module + " " + "cmd:" + cmd);
                        }
                    }
                }
            }
        }
        return o;
    }
}
