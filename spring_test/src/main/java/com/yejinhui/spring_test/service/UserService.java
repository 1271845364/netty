package com.yejinhui.spring_test.service;

import com.yejinhui.spring_test.annotation.SocketCmd;
import com.yejinhui.spring_test.annotation.SocketModule;

/**
 * 用户模块
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 21:09
 */
@SocketModule(module = 1)
public interface UserService {

    /**
     * 登录
     */
    @SocketCmd(cmd = 1)
    void login();

    @SocketCmd(cmd = 2)
    void getInfo();
}
