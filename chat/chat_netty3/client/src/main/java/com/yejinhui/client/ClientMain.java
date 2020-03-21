package com.yejinhui.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.yejinhui.client.swing.Swingclient;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 15:35
 */
public class ClientMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Swingclient swing = applicationContext.getBean(Swingclient.class);
        swing.setVisible(true);
    }
}
