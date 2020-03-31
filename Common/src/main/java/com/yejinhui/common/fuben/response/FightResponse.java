package com.yejinhui.common.fuben.response;

import com.yejinhui.common.serial.Serializer;

/**
 * 攻打响应
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 11:50
 */
public class FightResponse extends Serializer {

    /**
     * 获取的金币
     */
    private int gold;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    protected void read() {
        this.gold = readInt();
    }

    @Override
    protected void write() {
        writeInt(this.gold);
    }

    @Override
    public String toString() {
        return "FightResponse{" +
                "gold=" + gold +
                '}';
    }
}
