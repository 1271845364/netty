package com.yejinhui.common.module.player.request;

import com.yejinhui.common.core.serial.Serializer;

/**
 * 注册请求
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 22:30
 */
public class RegisterRequest extends Serializer {

    /**
     * 用户名
     */
    private String playerName;

    /**
     * 密码
     */
    private String password;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void read() {
        this.playerName = readString();
        this.password = readString();
    }

    @Override
    protected void write() {
        writeString(this.playerName);
        writeString(this.password);
    }
    
}
