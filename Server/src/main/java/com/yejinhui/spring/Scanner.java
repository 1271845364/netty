package com.yejinhui.spring;

import com.yejinhui.spring.annotation.SocketCmd;
import com.yejinhui.spring.annotation.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 扫描器实现spring的bean的生命周期
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 21:36
 */
@Component
public class Scanner implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Class<?>[] interfaces = beanClass.getInterfaces();
        //扫描所有的接口
        for (Class<?> anInterface : interfaces) {
            SocketModule socketModule = anInterface.getAnnotation(SocketModule.class);
            if (socketModule == null) {
                continue;
            }
            Method[] methods = anInterface.getMethods();
            if (methods != null && methods.length > 0) {
                for (Method method : methods) {
                    SocketCmd socketCmd = method.getAnnotation(SocketCmd.class);
                    if (socketCmd == null) {
                        continue;
                    }
                    //处理数据
                    short module = socketModule.module();
                    short cmd = socketCmd.cmd();

                    //将bean和method抽象成一个执行器
                    Invoker invoker = Invoker.valueOf(bean, method);
                    //将Invoker保存起来，写个holder
                    if (InvokerHolder.getInvoker(module, cmd) == null) {
                        InvokerHolder.addInvoker(module, cmd, invoker);
                    } else {
                        System.out.println("重复注册执行器module:" + module + "  cmd:" + cmd);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
