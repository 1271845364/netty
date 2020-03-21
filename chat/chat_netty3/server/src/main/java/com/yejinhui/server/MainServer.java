package com.yejinhui.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 9:51
 */
public class MainServer {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Server server = applicationContext.getBean(Server.class);

        server.start();
    }
}
