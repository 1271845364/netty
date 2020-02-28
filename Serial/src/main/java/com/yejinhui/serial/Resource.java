package com.yejinhui.serial;

import com.yejinhui.serial.core.Serializer;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/25 22:36
 */
public class Resource extends Serializer {

    private int gold;

    @Override
    protected void read() {
        this.gold = readInt();
    }

    @Override
    protected void write() {
        writeInt(gold);
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "gold=" + gold +
                '}';
    }
}
