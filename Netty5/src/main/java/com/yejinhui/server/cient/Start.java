package com.yejinhui.server.cient;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/23 11:00
 */
public class Start {

    public static void main(String[] args) {
        MultiClient multiClient = new MultiClient();
        multiClient.init(5);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("请输入：");
                String msg = bufferedReader.readLine();
                multiClient.nextChannel().writeAndFlush(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
