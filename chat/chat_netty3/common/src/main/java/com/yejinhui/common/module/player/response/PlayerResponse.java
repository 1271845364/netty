package com.yejinhui.common.module.player.response;

import com.yejinhui.common.core.serial.Serializer;

/**
 * 玩家响应
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/3/1 22:34
 */
public class PlayerResponse extends Serializer {

    /**
     * 玩家id
     */
    private long playerId;

    /**
     * 用户名
     */
    private String playerName;

    /**
     * 等级
     */
    private int level;

    /**
     * 经验
     */
    private int exp;

    @Override
    protected void read() {
        this.playerId = readLong();
        this.playerName = readString();
        this.level = readInt();
        this.exp = readInt();
    }

    @Override
    protected void write() {
        writeLong(this.playerId);
        writeString(this.playerName);
        writeInt(this.level);
        writeInt(this.exp);
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
