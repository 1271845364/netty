package com.yejinhui.client.module.player.handler;

import com.yejinhui.client.swing.ResultCodeTip;
import com.yejinhui.client.swing.Swingclient;
import com.yejinhui.common.core.model.ResultCode;
import com.yejinhui.common.module.player.response.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/21 16:46
 */
@Component
public class PlayerHandlerImpl implements PlayerHandler {

    @Autowired
    private Swingclient swingclient;

    @Autowired
    private ResultCodeTip resultCodeTip;

    @Override
    public void registerAndLogin(int resultCode, byte[] data) {
        if (resultCode == ResultCode.SUCCESS) {
            PlayerResponse playerResponse = new PlayerResponse();
            playerResponse.readFromBytes(data);

            swingclient.setPlayerResponse(playerResponse);
            swingclient.getTips().setText("注册登录成功");
        }else{
            swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
        }
    }

    @Override
    public void login(int resultCode, byte[] data) {
        if (resultCode == ResultCode.SUCCESS) {
            PlayerResponse playerResponse = new PlayerResponse();
            playerResponse.readFromBytes(data);

            swingclient.setPlayerResponse(playerResponse);
            swingclient.getTips().setText("登录成功");
        }else{
            swingclient.getTips().setText(resultCodeTip.getTipContent(resultCode));
        }
    }
}
