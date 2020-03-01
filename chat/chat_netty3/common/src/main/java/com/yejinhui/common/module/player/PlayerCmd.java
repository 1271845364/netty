package com.yejinhui.common.module.player;

/**
 * 玩家模块
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 22:26
 */
public interface PlayerCmd {

    /**
     * 注册并登陆账号
     */
    short REGISTER_AND_LOGIN = 1;

    /**
     * 登陆账号
     */
    short LOGIN = 2;

}
