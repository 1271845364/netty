package com.yejinhui.server.module.player.handler;

import com.yejinhui.common.core.model.Result;
import com.yejinhui.common.core.model.ResultCode;
import com.yejinhui.common.core.session.Session;
import com.yejinhui.common.module.player.request.RegisterRequest;
import com.yejinhui.common.module.player.response.PlayerResponse;
import com.yejinhui.server.module.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author ye.jinhui
 * @description 玩家模块
 * @program netty-hello
 * @create 2020/3/21 13:38
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler {

    @Autowired
    private PlayerService playerService;

    @Override
    public Result<PlayerResponse> registerAndLogin(Session session, byte[] data) {
        PlayerResponse playerResponse = null;
        try {
            //反序列化
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.readFromBytes(data);

            //参数判空
            if (StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassword())) {
                return Result.ERROR(ResultCode.PLAYERNAME_NULL);
            }

            //执行业务
            playerResponse = playerService.registerAndLogin(session, registerRequest.getPlayerName(), registerRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ERROR(ResultCode.UNKNOWN_EXCEPTION);
        }
        return Result.SUCCESS(playerResponse);
    }

    @Override
    public Result<PlayerResponse> login(Session session, byte[] data) {
        PlayerResponse playerResponse = null;
        try {
            //反序列化
            RegisterRequest registerRequest = new RegisterRequest();
            registerRequest.readFromBytes(data);

            //参数判空
            if (StringUtils.isEmpty(registerRequest.getPlayerName()) || StringUtils.isEmpty(registerRequest.getPassword())) {
                return Result.ERROR(ResultCode.PLAYERNAME_NULL);
            }

            //执行业务
            playerResponse = playerService.login(session, registerRequest.getPlayerName(), registerRequest.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ERROR(ResultCode.UNKNOWN_EXCEPTION);
        }
        return Result.SUCCESS(playerResponse);
    }
}
