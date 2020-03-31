package com.yejinhui.service;

import org.springframework.stereotype.Component;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 23:11
 */
@Component
public class ServiceImpl implements Service {

    @Override
    public void cmd1() {
        System.out.println("cmd1");
    }

    @Override
    public void cmd2() {
        System.out.println("cmd2");
    }
}
