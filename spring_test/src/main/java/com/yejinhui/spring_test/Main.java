package com.yejinhui.spring_test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 22:54
 */
public class Main {

    public static void main(String[] args) {
        //加载spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Invoker invoker = InvokerHolder.getInvoker((short) 1, (short) 2);
        invoker.invoke(null);

    }
}
