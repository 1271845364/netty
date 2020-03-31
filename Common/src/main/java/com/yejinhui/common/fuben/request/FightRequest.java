package com.yejinhui.common.fuben.request;

import com.yejinhui.common.serial.Serializer;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 11:24
 */
public class FightRequest extends Serializer {

    /**
     * 副本id
     */
    private int fubenId;

    /**
     * 次数
     */
    private int count;

    public int getFubenId() {
        return fubenId;
    }

    public void setFubenId(int fubenId) {
        this.fubenId = fubenId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    protected void read() {
        this.fubenId = readInt();
        this.count = readInt();
    }

    @Override
    protected void write() {
        writeInt(this.fubenId);
        writeInt(this.count);
    }

    @Override
    public String toString() {
        return "FightRequest{" +
                "fubenId=" + fubenId +
                ", count=" + count +
                '}';
    }
}
