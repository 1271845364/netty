package com.yejinhui.client.module.player.handler;

import com.yejinhui.common.core.annotation.SocketCommand;
import com.yejinhui.common.core.annotation.SocketModule;
import com.yejinhui.common.module.ModuleId;
import com.yejinhui.common.module.player.PlayerCmd;

/**
 * @author ye.jinhui
 * @description 玩家模块
 * @program netty-hello
 * @create 2020/3/21 16:43
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {

    /**
     * 创建并登录账号
     *
     * @param resultCode
     * @param data
     */
    @SocketCommand(command = PlayerCmd.REGISTER_AND_LOGIN)
    void registerAndLogin(int resultCode, byte[] data);

    /**
     * 登录账号
     *
     * @param resultCode
     * @param data
     */
    void login(int resultCode, byte[] data);
}
