package com.yejinhui.spring_test.service;

import org.springframework.stereotype.Component;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 21:24
 */
@Component
public class UserServiceImpl implements UserService {

    @Override
    public void login() {
        System.out.println("login");
    }

    @Override
    public void getInfo() {
        System.out.println("getInfo");
    }

}
