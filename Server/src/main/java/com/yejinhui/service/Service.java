package com.yejinhui.service;

import com.yejinhui.spring.annotation.SocketCmd;
import com.yejinhui.spring.annotation.SocketModule;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 23:10
 */
@SocketModule(module = 1)
public interface Service {

    @SocketCmd(cmd = 1)
    void cmd1();

    @SocketCmd(cmd = 2)
    void cmd2();
}
