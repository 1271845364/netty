package com.yejinhui.client;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @author ye.jinhui
 * @description
 * @program netty-hello
 * @create 2020/2/29 17:08
 */
public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 10101);
        String message = "hello";
        byte[] bytes = message.getBytes();
        //byteBuffer大小为length+data
        int length = bytes.length;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + length);
        byteBuffer.putInt(length);
        byteBuffer.put(bytes);
        for (int i = 0; i < 1000; i++) {
            socket.getOutputStream().write(byteBuffer.array());
        }
        socket.close();
    }

}
