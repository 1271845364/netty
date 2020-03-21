package com.yejinhui.server.module.player.service;

import com.yejinhui.common.core.session.Session;
import com.yejinhui.common.module.player.response.PlayerResponse;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 12:29
 */
public interface PlayerService {

    /**
     * 注册和登录用户
     *
     * @param session
     * @param playerName
     * @param password
     * @return
     */
    PlayerResponse registerAndLogin(Session session, String playerName, String password);

    /**
     * 登录
     *
     * @param session
     * @param playerName
     * @param password
     * @return
     */
    PlayerResponse login(Session session, String playerName, String password);
}
