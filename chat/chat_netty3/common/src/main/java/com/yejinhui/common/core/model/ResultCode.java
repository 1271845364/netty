package com.yejinhui.common.core.model;

/**
 * 结果码
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 16:59
 */
public interface ResultCode {

    /**
     * 成功
     */
    int SUCCESS = 0;

    /**
     * 找不到指令
     */
    int NO_INVOKER = 1;

    /**
     * 参数异常
     */
    int ARGUMENT_ERROR = 2;

    /**
     * 未知异常
     */
    int UNKNOWN_EXCEPTION = 3;

    /**
     * 玩家名字或密码不能为空
     */
    int PLAYERNAME_NULL = 4;

    /**
     * 玩家名字已经存在
     */
    int PLAYERNAME_EXIST = 5;

    /**
     * 玩家名字不存在
     */
    int PLAYERNAME_NO_EXIST = 6;

    /**
     * 密码错误
     */
    int PASSWORD_ERROR = 7;

    /**
     * 已经登录
     */
    int HAS_LOGIN = 8;

    /**
     * 登录失败
     */
    int LOGIN_FAIL = 9;

    /**
     * 玩家不在线
     */
    int PLAYER_NO_ONLINE = 10;

    /**
     * 请先登录
     */
    int LOGIN_PLEASE = 11;

    /**
     * 不能私聊自己
     */
    int CAN_CHAT_YOURSELF = 12;
}
