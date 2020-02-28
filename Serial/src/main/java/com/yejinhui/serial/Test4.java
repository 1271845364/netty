package com.yejinhui.serial;

import java.util.Arrays;

/**
 * 自定义序列化测试
 *
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/25 22:26
 */
public class Test4 {

    public static void main(String[] args) {
        Player player = new Player();
        player.setPlayerId(10001);
        player.setAge(21);
        player.getSkills().add(101);
        player.getResource().setGold(12);
        //序列化
        byte[] bytes = player.getBytes();
        System.out.println(Arrays.toString(bytes));

        Player player1 = new Player();
        //反序列化
        player1.readFromBytes(bytes);
        System.out.println(player1);
        System.out.println(player1.getResource());
    }

}
