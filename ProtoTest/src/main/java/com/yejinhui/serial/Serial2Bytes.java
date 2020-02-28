package com.yejinhui.serial;

import java.util.Arrays;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/26 21:40
 */
public class Serial2Bytes {

    public static void main(String[] args) {
        byte[] bytes = toBytes();
        toPlayer(bytes);
    }

    /**
     * 序列化
     *
     * @return
     */
    private static byte[] toBytes() {
        Player player = new Player(101, 20, "peter");
        player.getSkills().add(1001);

        //获取字节数组
        byte[] bytes = player.getBytes();
        System.out.println(Arrays.toString(bytes));
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param bytes
     */
    public static void toPlayer(byte[] bytes) {
        Player player = new Player();
        player.readFromBytes(bytes);

        System.out.println(player);
    }

}
