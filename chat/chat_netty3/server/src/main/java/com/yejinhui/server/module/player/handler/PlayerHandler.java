package com.yejinhui.server.module.player.handler;

import com.yejinhui.common.core.annotation.SocketCommand;
import com.yejinhui.common.core.annotation.SocketModule;
import com.yejinhui.common.core.model.Result;
import com.yejinhui.common.core.session.Session;
import com.yejinhui.common.module.ModuleId;
import com.yejinhui.common.module.player.PlayerCmd;
import com.yejinhui.common.module.player.response.PlayerResponse;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 12:55
 */
@SocketModule(module = ModuleId.PLAYER)
public interface PlayerHandler {

    /**
     * 创建并登录账号
     *
     * @param session
     * @param data
     * @return
     */
    @SocketCommand(command = PlayerCmd.REGISTER_AND_LOGIN)
    Result<PlayerResponse> registerAndLogin(Session session, byte[] data);

    /**
     * 登录账号
     *
     * @param session
     * @param data
     * @return
     */
    Result<PlayerResponse> login(Session session, byte[] data);
}
